package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;
import net.mineshafts.mnm.enums.charcreationenums.RaceEnum;

import static net.mineshafts.mnm.networking.ModMessages.*;

public class PlayerRace {
    public static boolean existingRace = false;
    public static boolean existingSubRace = false;
    private static RaceEnum race;
    private static CharStatEnum subRace;

    public static RaceEnum getRace() {
        return race;
    }
    public static void setRace(RaceEnum race) {
        PlayerRace.race = race;
        existingRace = true;
    }
    public static void setRace(String name){
        PlayerRace.race = (RaceEnum) RaceEnum.SgetEnum(name);
        existingRace = true;
    }

    public static CharStatEnum getSubRace() {
        return subRace;
    }
    public static void setSubRace(CharStatEnum subRace) {
        PlayerRace.subRace = subRace;
        existingSubRace = true;
        setRace();
    }
    public static void setSubRace(String name){
        PlayerRace.subRace = race.getSubEnum().getEnum(name);
        existingSubRace = true;
    }
    public static void setRace(){
        if (!existingRace)
            return;
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeBoolean(existingSubRace);
        packet.writeString(race.getTranslationKey());
        if (existingSubRace)
            packet.writeString(subRace.getTranslationKey());
        ClientPlayNetworking.send(SET_RACE,packet);
    }
}
