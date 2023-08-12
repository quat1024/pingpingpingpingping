# PingPingPingPingPing

Packet spam debugger.

## Usage

Log in, do some stuff, log out. PingPingPingPingPing will keep track of the packets you received and print a report to the log after logging out.

A report is printed during login (any time before `ClientPlayConnectionEvents.JOIN` is fired) containing the packets exchanged during the login/ready process, and another report is printed during disconnection.

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