package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.StatType;
import net.mineshafts.mnm.networking.ModMessages;

import java.util.Objects;

public class AbilityScoreIncrease {
    public void increaseScores(){
        int[] newScores = new int[6];
        for (int i=0;i<6;i++)
            newScores[i] = PlayerAbilityScores.getScores()[i] + this.scores[i];
        PlayerAbilityScores.setScores(newScores);
    }
    private int[] scores = new int[6];
    public AbilityScoreIncrease(int...scores){
        if(scores.length==6)
            this.scores = scores;
    }
    public int getScore(String name) {
        int result = 0;
        for (int i = 0; i < StatType.values().length; i++)
        {
            if (Objects.equals(name, StatType.values()[i].getTranslationKey()))
                result = scores[i];
        }

        return result;
    }
    public void setScore(String name, int value) {
        for (int i = 0; i < StatType.values().length; i++)
        {
            if (Objects.equals(name, StatType.values()[i].getId())) {
                scores[i] = value;
                PacketByteBuf buff = PacketByteBufs.create();
                buff.writeInt(value);
                if (i==0)
                    ServerPlayNetworking.send(MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(MinecraftClient.getInstance().player.getUuid()), ModMessages.STRENGTH_SET, buff);
            }
        }

    }
    public int[] getScores(){
        return scores;
    }
}
