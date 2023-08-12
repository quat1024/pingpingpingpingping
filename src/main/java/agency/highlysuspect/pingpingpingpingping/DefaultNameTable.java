package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;

import java.util.HashMap;
import java.util.Map;

//big table of obfuscation mappings
public class DefaultNameTable {
	public static final Map<Class<?>, String> TABLE = new HashMap<>();
	
	//yeah yeah theres some ones that arent packets, i dont really care
	static {
		TABLE.put(ClientboundAddEntityPacket.class, "ClientboundAddEntityPacket");
		TABLE.put(ClientboundAddExperienceOrbPacket.class, "ClientboundAddExperienceOrbPacket");
		TABLE.put(ClientboundAddPlayerPacket.class, "ClientboundAddPlayerPacket");
		TABLE.put(ClientboundAnimatePacket.class, "ClientboundAnimatePacket");
		TABLE.put(ClientboundAwardStatsPacket.class, "ClientboundAwardStatsPacket");
		TABLE.put(ClientboundBlockChangedAckPacket.class, "ClientboundBlockChangedAckPacket");
		TABLE.put(ClientboundBlockDestructionPacket.class, "ClientboundBlockDestructionPacket");
		TABLE.put(ClientboundBlockEntityDataPacket.class, "ClientboundBlockEntityDataPacket");
		TABLE.put(ClientboundBlockEventPacket.class, "ClientboundBlockEventPacket");
		TABLE.put(ClientboundBlockUpdatePacket.class, "ClientboundBlockUpdatePacket");
//		TABLE.put(ClientboundBossEventPacket.1.class, "ClientboundBossEventPacket.1");
//		TABLE.put(ClientboundBossEventPacket.AddOperation.class, "ClientboundBossEventPacket");
		TABLE.put(ClientboundBossEventPacket.class, "ClientboundBossEventPacket");
		TABLE.put(ClientboundBossEventPacket.Handler.class, "ClientboundBossEventPacket.Handler");
//		TABLE.put(ClientboundBossEventPacket.Operation.class, "ClientboundBossEventPacket");
//		TABLE.put(ClientboundBossEventPacket.OperationType.class, "ClientboundBossEventPacket");
//		TABLE.put(ClientboundBossEventPacket.UpdateNameOperation.class, "ClientboundBossEventPacket");
//		TABLE.put(ClientboundBossEventPacket.UpdateProgressOperation.class, "ClientboundBossEventPacket");
//		TABLE.put(ClientboundBossEventPacket.UpdatePropertiesOperation.class, "ClientboundBossEventPacket");
//		TABLE.put(ClientboundBossEventPacket.UpdateStyleOperation.class, "ClientboundBossEventPacket");
		TABLE.put(ClientboundBundlePacket.class, "ClientboundBundlePacket");
		TABLE.put(ClientboundChangeDifficultyPacket.class, "ClientboundChangeDifficultyPacket");
		TABLE.put(ClientboundChunksBiomesPacket.ChunkBiomeData.class, "ClientboundChunksBiomesPacket.ChunkBiomeData");
		TABLE.put(ClientboundChunksBiomesPacket.class, "ClientboundChunksBiomesPacket");
		TABLE.put(ClientboundClearTitlesPacket.class, "ClientboundClearTitlesPacket");
//		TABLE.put(ClientboundCommandsPacket.ArgumentNodeStub.class, "ClientboundCommandsPacket");
		TABLE.put(ClientboundCommandsPacket.class, "ClientboundCommandsPacket");
//		TABLE.put(ClientboundCommandsPacket.Entry.class, "ClientboundCommandsPacket");
//		TABLE.put(ClientboundCommandsPacket.LiteralNodeStub.class, "ClientboundCommandsPacket");
//		TABLE.put(ClientboundCommandsPacket.NodeResolver.class, "ClientboundCommandsPacket");
//		TABLE.put(ClientboundCommandsPacket.NodeStub.class, "ClientboundCommandsPacket");
		TABLE.put(ClientboundCommandSuggestionsPacket.class, "ClientboundCommandSuggestionsPacket");
		TABLE.put(ClientboundContainerClosePacket.class, "ClientboundContainerClosePacket");
		TABLE.put(ClientboundContainerSetContentPacket.class, "ClientboundContainerSetContentPacket");
		TABLE.put(ClientboundContainerSetDataPacket.class, "ClientboundContainerSetDataPacket");
		TABLE.put(ClientboundContainerSetSlotPacket.class, "ClientboundContainerSetSlotPacket");
		TABLE.put(ClientboundCooldownPacket.class, "ClientboundCooldownPacket");
		TABLE.put(ClientboundCustomChatCompletionsPacket.Action.class, "ClientboundCustomChatCompletionsPacket.Action");
		TABLE.put(ClientboundCustomChatCompletionsPacket.class, "ClientboundCustomChatCompletionsPacket");
		TABLE.put(ClientboundCustomPayloadPacket.class, "ClientboundCustomPayloadPacket");
		TABLE.put(ClientboundDamageEventPacket.class, "ClientboundDamageEventPacket");
		TABLE.put(ClientboundDeleteChatPacket.class, "ClientboundDeleteChatPacket");
		TABLE.put(ClientboundDisconnectPacket.class, "ClientboundDisconnectPacket");
		TABLE.put(ClientboundDisguisedChatPacket.class, "ClientboundDisguisedChatPacket");
		TABLE.put(ClientboundEntityEventPacket.class, "ClientboundEntityEventPacket");
		TABLE.put(ClientboundExplodePacket.class, "ClientboundExplodePacket");
		TABLE.put(ClientboundForgetLevelChunkPacket.class, "ClientboundForgetLevelChunkPacket");
		TABLE.put(ClientboundGameEventPacket.class, "ClientboundGameEventPacket");
		TABLE.put(ClientboundGameEventPacket.Type.class, "ClientboundGameEventPacket.Type");
		TABLE.put(ClientboundHorseScreenOpenPacket.class, "ClientboundHorseScreenOpenPacket");
		TABLE.put(ClientboundHurtAnimationPacket.class, "ClientboundHurtAnimationPacket");
		TABLE.put(ClientboundInitializeBorderPacket.class, "ClientboundInitializeBorderPacket");
		TABLE.put(ClientboundKeepAlivePacket.class, "ClientboundKeepAlivePacket");
//		TABLE.put(ClientboundLevelChunkPacketData.BlockEntityInfo.class, "ClientboundLevelChunkPacketData");
		TABLE.put(ClientboundLevelChunkPacketData.BlockEntityTagOutput.class, "ClientboundLevelChunkPacketData.BlockEntityTagOutput");
		TABLE.put(ClientboundLevelChunkPacketData.class, "ClientboundLevelChunkPacketData");
		TABLE.put(ClientboundLevelChunkWithLightPacket.class, "ClientboundLevelChunkWithLightPacket");
		TABLE.put(ClientboundLevelEventPacket.class, "ClientboundLevelEventPacket");
		TABLE.put(ClientboundLevelParticlesPacket.class, "ClientboundLevelParticlesPacket");
		TABLE.put(ClientboundLightUpdatePacket.class, "ClientboundLightUpdatePacket");
		TABLE.put(ClientboundLightUpdatePacketData.class, "ClientboundLightUpdatePacketData");
		TABLE.put(ClientboundLoginPacket.class, "ClientboundLoginPacket");
		TABLE.put(ClientboundMapItemDataPacket.class, "ClientboundMapItemDataPacket");
		TABLE.put(ClientboundMerchantOffersPacket.class, "ClientboundMerchantOffersPacket");
		TABLE.put(ClientboundMoveEntityPacket.class, "ClientboundMoveEntityPacket");
		TABLE.put(ClientboundMoveEntityPacket.Pos.class, "ClientboundMoveEntityPacket.Pos");
		TABLE.put(ClientboundMoveEntityPacket.PosRot.class, "ClientboundMoveEntityPacket.PosRot");
		TABLE.put(ClientboundMoveEntityPacket.Rot.class, "ClientboundMoveEntityPacket.Rot");
		TABLE.put(ClientboundMoveVehiclePacket.class, "ClientboundMoveVehiclePacket");
		TABLE.put(ClientboundOpenBookPacket.class, "ClientboundOpenBookPacket");
		TABLE.put(ClientboundOpenScreenPacket.class, "ClientboundOpenScreenPacket");
		TABLE.put(ClientboundOpenSignEditorPacket.class, "ClientboundOpenSignEditorPacket");
		TABLE.put(ClientboundPingPacket.class, "ClientboundPingPacket");
		TABLE.put(ClientboundPlaceGhostRecipePacket.class, "ClientboundPlaceGhostRecipePacket");
		TABLE.put(ClientboundPlayerAbilitiesPacket.class, "ClientboundPlayerAbilitiesPacket");
		TABLE.put(ClientboundPlayerChatPacket.class, "ClientboundPlayerChatPacket");
		TABLE.put(ClientboundPlayerCombatEndPacket.class, "ClientboundPlayerCombatEndPacket");
		TABLE.put(ClientboundPlayerCombatEnterPacket.class, "ClientboundPlayerCombatEnterPacket");
		TABLE.put(ClientboundPlayerCombatKillPacket.class, "ClientboundPlayerCombatKillPacket");
		TABLE.put(ClientboundPlayerInfoRemovePacket.class, "ClientboundPlayerInfoRemovePacket");
		TABLE.put(ClientboundPlayerInfoUpdatePacket.Action.class, "ClientboundPlayerInfoUpdatePacket.Action");
		TABLE.put(ClientboundPlayerInfoUpdatePacket.Action.Reader.class, "ClientboundPlayerInfoUpdatePacket.Action.Reader");
		TABLE.put(ClientboundPlayerInfoUpdatePacket.Action.Writer.class, "ClientboundPlayerInfoUpdatePacket.Action.Writer");
		TABLE.put(ClientboundPlayerInfoUpdatePacket.class, "ClientboundPlayerInfoUpdatePacket");
//		TABLE.put(ClientboundPlayerInfoUpdatePacket.EntryBuilder.class, "ClientboundPlayerInfoUpdatePacket");
		TABLE.put(ClientboundPlayerInfoUpdatePacket.Entry.class, "ClientboundPlayerInfoUpdatePacket.Entry");
		TABLE.put(ClientboundPlayerLookAtPacket.class, "ClientboundPlayerLookAtPacket");
		TABLE.put(ClientboundPlayerPositionPacket.class, "ClientboundPlayerPositionPacket");
		TABLE.put(ClientboundRecipePacket.class, "ClientboundRecipePacket");
		TABLE.put(ClientboundRecipePacket.State.class, "ClientboundRecipePacket.State");
		TABLE.put(ClientboundRemoveEntitiesPacket.class, "ClientboundRemoveEntitiesPacket");
		TABLE.put(ClientboundRemoveMobEffectPacket.class, "ClientboundRemoveMobEffectPacket");
		TABLE.put(ClientboundResourcePackPacket.class, "ClientboundResourcePackPacket");
		TABLE.put(ClientboundRespawnPacket.class, "ClientboundRespawnPacket");
		TABLE.put(ClientboundRotateHeadPacket.class, "ClientboundRotateHeadPacket");
		TABLE.put(ClientboundSectionBlocksUpdatePacket.class, "ClientboundSectionBlocksUpdatePacket");
		TABLE.put(ClientboundSelectAdvancementsTabPacket.class, "ClientboundSelectAdvancementsTabPacket");
		TABLE.put(ClientboundServerDataPacket.class, "ClientboundServerDataPacket");
		TABLE.put(ClientboundSetActionBarTextPacket.class, "ClientboundSetActionBarTextPacket");
		TABLE.put(ClientboundSetBorderCenterPacket.class, "ClientboundSetBorderCenterPacket");
		TABLE.put(ClientboundSetBorderLerpSizePacket.class, "ClientboundSetBorderLerpSizePacket");
		TABLE.put(ClientboundSetBorderSizePacket.class, "ClientboundSetBorderSizePacket");
		TABLE.put(ClientboundSetBorderWarningDelayPacket.class, "ClientboundSetBorderWarningDelayPacket");
		TABLE.put(ClientboundSetBorderWarningDistancePacket.class, "ClientboundSetBorderWarningDistancePacket");
		TABLE.put(ClientboundSetCameraPacket.class, "ClientboundSetCameraPacket");
		TABLE.put(ClientboundSetCarriedItemPacket.class, "ClientboundSetCarriedItemPacket");
		TABLE.put(ClientboundSetChunkCacheCenterPacket.class, "ClientboundSetChunkCacheCenterPacket");
		TABLE.put(ClientboundSetChunkCacheRadiusPacket.class, "ClientboundSetChunkCacheRadiusPacket");
		TABLE.put(ClientboundSetDefaultSpawnPositionPacket.class, "ClientboundSetDefaultSpawnPositionPacket");
		TABLE.put(ClientboundSetDisplayObjectivePacket.class, "ClientboundSetDisplayObjectivePacket");
		TABLE.put(ClientboundSetEntityDataPacket.class, "ClientboundSetEntityDataPacket");
		TABLE.put(ClientboundSetEntityLinkPacket.class, "ClientboundSetEntityLinkPacket");
		TABLE.put(ClientboundSetEntityMotionPacket.class, "ClientboundSetEntityMotionPacket");
		TABLE.put(ClientboundSetEquipmentPacket.class, "ClientboundSetEquipmentPacket");
		TABLE.put(ClientboundSetExperiencePacket.class, "ClientboundSetExperiencePacket");
		TABLE.put(ClientboundSetHealthPacket.class, "ClientboundSetHealthPacket");
		TABLE.put(ClientboundSetObjectivePacket.class, "ClientboundSetObjectivePacket");
		TABLE.put(ClientboundSetPassengersPacket.class, "ClientboundSetPassengersPacket");
		TABLE.put(ClientboundSetPlayerTeamPacket.Action.class, "ClientboundSetPlayerTeamPacket.Action");
		TABLE.put(ClientboundSetPlayerTeamPacket.class, "ClientboundSetPlayerTeamPacket");
		TABLE.put(ClientboundSetPlayerTeamPacket.Parameters.class, "ClientboundSetPlayerTeamPacket.Parameters");
		TABLE.put(ClientboundSetScorePacket.class, "ClientboundSetScorePacket");
		TABLE.put(ClientboundSetSimulationDistancePacket.class, "ClientboundSetSimulationDistancePacket");
		TABLE.put(ClientboundSetSubtitleTextPacket.class, "ClientboundSetSubtitleTextPacket");
		TABLE.put(ClientboundSetTimePacket.class, "ClientboundSetTimePacket");
		TABLE.put(ClientboundSetTitlesAnimationPacket.class, "ClientboundSetTitlesAnimationPacket");
		TABLE.put(ClientboundSetTitleTextPacket.class, "ClientboundSetTitleTextPacket");
		TABLE.put(ClientboundSoundEntityPacket.class, "ClientboundSoundEntityPacket");
		TABLE.put(ClientboundSoundPacket.class, "ClientboundSoundPacket");
		TABLE.put(ClientboundStopSoundPacket.class, "ClientboundStopSoundPacket");
		TABLE.put(ClientboundSystemChatPacket.class, "ClientboundSystemChatPacket");
		TABLE.put(ClientboundTabListPacket.class, "ClientboundTabListPacket");
		TABLE.put(ClientboundTagQueryPacket.class, "ClientboundTagQueryPacket");
		TABLE.put(ClientboundTakeItemEntityPacket.class, "ClientboundTakeItemEntityPacket");
		TABLE.put(ClientboundTeleportEntityPacket.class, "ClientboundTeleportEntityPacket");
		TABLE.put(ClientboundUpdateAdvancementsPacket.class, "ClientboundUpdateAdvancementsPacket");
		TABLE.put(ClientboundUpdateAttributesPacket.AttributeSnapshot.class, "ClientboundUpdateAttributesPacket.AttributeSnapshot");
		TABLE.put(ClientboundUpdateAttributesPacket.class, "ClientboundUpdateAttributesPacket");
		TABLE.put(ClientboundUpdateEnabledFeaturesPacket.class, "ClientboundUpdateEnabledFeaturesPacket");
		TABLE.put(ClientboundUpdateMobEffectPacket.class, "ClientboundUpdateMobEffectPacket");
		TABLE.put(ClientboundUpdateRecipesPacket.class, "ClientboundUpdateRecipesPacket");
		TABLE.put(ClientboundUpdateTagsPacket.class, "ClientboundUpdateTagsPacket");
		TABLE.put(ClientGamePacketListener.class, "ClientGamePacketListener");
	}
}