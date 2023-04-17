package net.mineshafts.mnm.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;
import net.mineshafts.mnm.networking.clientactions.PlayerInfoGetter;
import net.mineshafts.mnm.networking.packet.StartingEquipmentC2SPacket;
import net.mineshafts.mnm.playerdata.ServerPlayerData;

public class ModMessages {
    public static final Identifier GET_ITEM = new Identifier(MnM.ModId, "get_item");
    public static final Identifier RESET_NBT = new Identifier(MnM.ModId, "reset_nbt");
    public static final Identifier SET_ABILITY_SCORES = new Identifier(MnM.ModId,"set_ability_scores");
    public static final Identifier SET_RACE = new Identifier(MnM.ModId,"set_race");
    public static final Identifier SET_CLASS = new Identifier(MnM.ModId,"set_class");
    public static final Identifier SET_BACKGROUND = new Identifier(MnM.ModId,"set_background");
    public static final Identifier SET_PROFICIENCIES = new Identifier(MnM.ModId,"set_proficiencies");
    public static final Identifier BROADCAST_NBT = new Identifier(MnM.ModId, "broadcast_nbt");
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(GET_ITEM, StartingEquipmentC2SPacket::giveItems);
        ServerPlayNetworking.registerGlobalReceiver(RESET_NBT, StartingEquipmentC2SPacket::resetNBT);
        ServerPlayNetworking.registerGlobalReceiver(SET_ABILITY_SCORES, ServerPlayerData::setScores);
        ServerPlayNetworking.registerGlobalReceiver(SET_RACE, ServerPlayerData::setRace);
        ServerPlayNetworking.registerGlobalReceiver(SET_CLASS, ServerPlayerData::setClass);
        ServerPlayNetworking.registerGlobalReceiver(SET_BACKGROUND, ServerPlayerData::setBackground);
        ServerPlayNetworking.registerGlobalReceiver(SET_PROFICIENCIES, ServerPlayerData::setProficiencies);
        ServerPlayNetworking.registerGlobalReceiver(BROADCAST_NBT,ServerPlayerData::broadcastNbt);
    }
    public static final Identifier SEND_PLAYER_INFO = new Identifier(MnM.ModId,"send_player_info");
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SEND_PLAYER_INFO,PlayerInfoGetter::getPlayerInfo);
    }
}
