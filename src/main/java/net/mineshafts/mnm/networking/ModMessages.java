package net.mineshafts.mnm.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;
import net.mineshafts.mnm.networking.packet.StartingEquipmentC2SPacket;
import net.mineshafts.mnm.playerdata.ServerPlayerData;

public class ModMessages {
    public static final Identifier GET_ITEM = new Identifier(MnM.ModId, "get_item");
    public static final Identifier RESET_NBT = new Identifier(MnM.ModId, "reset_nbt");
    public static final Identifier SET_ABILITY_SCORES = new Identifier(MnM.ModId,"set_ability_scores");
    public static final Identifier SET_RACE = new Identifier(MnM.ModId,"set_race");
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(GET_ITEM, StartingEquipmentC2SPacket::giveItems);
        ServerPlayNetworking.registerGlobalReceiver(RESET_NBT, StartingEquipmentC2SPacket::resetNBT);
        ServerPlayNetworking.registerGlobalReceiver(SET_ABILITY_SCORES, ServerPlayerData::setScores);
        ServerPlayNetworking.registerGlobalReceiver(SET_RACE, ServerPlayerData::setRace);
    }
    public static void registerS2CPackets(){

    }
}
