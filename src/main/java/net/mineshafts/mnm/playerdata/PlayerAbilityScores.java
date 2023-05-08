package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import static net.mineshafts.mnm.networking.ModMessages.*;

public class PlayerAbilityScores {
    public static void setScores(int[] scores){
        if (scores.length!=6)
            throw new RuntimeException("Scores array is invalid length");
        PacketByteBuf packet = PacketByteBufs.create();
        for (int score: scores){
            packet.writeInt(score);
        }
        ClientPlayNetworking.send(SET_ABILITY_SCORES,packet);
    }
}
