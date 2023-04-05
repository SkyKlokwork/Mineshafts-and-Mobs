package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.StatType;
import net.mineshafts.mnm.networking.ModMessages;

import java.util.Objects;

public class PlayerAbilityScores {
    public static boolean existingScores = false;
    private static int[] scores = new int[6];
    public static int getScore(String name) {
        int result = 0;
        for (int i = 0; i < StatType.values().length; i++)
        {
            if (Objects.equals(name, StatType.values()[i].getId()))
                result = scores[i];
        }

        return result;
    }
    public static void setScore(String name, int value) {
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
    public static void setScores(int[] values){
        scores = values;
    }
    public static int[] getScores(){
        return scores;
    }
}
