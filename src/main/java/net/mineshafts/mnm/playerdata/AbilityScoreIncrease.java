package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.StatType;
import net.mineshafts.mnm.networking.ModMessages;

import java.util.Objects;

public class AbilityScoreIncrease {
    private int[] scores = new int[6];
    public AbilityScoreIncrease(int...scores){
        if(scores.length==6)
            this.scores = scores;
    }
    public int[] getScores(){
        return scores;
    }
}
