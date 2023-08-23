# PingPingPingPingPing

Clientside packet spam debugger.

## Usage

Run the client command `/pingpingpingpingping start` to start logging packets, run `/pingpingpingpingping stop` to finish and save a report. (If this command is too long, it's aliased to `/ping5` :wink:)

Packets are tracked by class name (vanilla names get reobfuscated to their mojang name). Some packets have "extractors" that provide additional context for each packet (for an entity update: which entity was updating, e.g.)

## Wishlist

* The extractor api is fucking terrible and hard to compose, some sort of "decorator" style api (all packets pass through all decorators) might b better
  * It's also annoying to have to strike a balance between "capturing enough data to be useful in the packet" and "breaking the collation algorithm so you get 1000 packets all seen 1 time each" need to Re think that as well
  * it's 2am and ive been Suffering from chronic sleepiness sorry

I honestly want to investigate a more heavyweight database solution as well (h2 or something) to allow for interesting queries and reports to be done

Basically i have some fuckin IDEAS but im just quickly hacking things together right now

## what does is the networking

ConnectScreen is what starts the actual multiplayer connection

things trickle into `Connection.configureSerialization`

* timeout: `ReadTimeoutHandler`
* splitter: `Varint21FrameDecoder`
* decoder: `PacketDecoder`
* prepender: `Varint21LengthFieldPrepender`
* encoder: `PacketEncoder`
* unbundler: `PacketBundleUnpacker`
* bundler: `PacketBundlePacker`
* packet_handler: the `Connection` itself

Incoming stuff is `timeout`, `splitter`, and `decoder` but its kinda odd to me why the pipeline directly connects incoming to outgoing.. like arent those 2 different things. Oh well

when encryption is enabled `decrypt` (CipherDecoder) goes before `splitter` and `encrypt` (CipherEncoder) goes before `prepender`

ReadTimeoutHandler is from netty

`Varint21FrameDecoder` is what actually creates the `FriendlyByteBuf` which is neat. It uses the `ByteToMessageDecoder` interface from netty, which is intended to adapt from byte bufs into more rich objects. It reads a varint length, reads that many bytes, then passes the bytebuf to the out parameter

`PacketDecoder` uhhhh, also turns the bytebuf into a `FriendlyByteBuf` no idea where the last one went, then reads another varint and uses it as packet id, then decodes the packet (!!). Actual packet decoding is in `ConnectionProtocol` which keeps a map from id->`Packet` constructors and dispatches based off the packet id.

The local server uses a totally different stub "network stack" (`connectToLocalServer`) that never actually writes anything to bytebufs. Importantly this means on loopback there's no meaningful sense of the "size" of a packet.