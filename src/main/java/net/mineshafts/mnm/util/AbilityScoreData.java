package net.mineshafts.mnm.util;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.MnM;
import net.mineshafts.mnm.networking.ModMessages;

public class AbilityScoreData {
    public static int addScore(IEntityDataSaver player, String name, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int value = nbt.getInt(name);
        if (value + amount <= 0)
            value = 0;
        else
            value += amount;

        int mod = (value/2) - 5;

        nbt.putInt(MnM.ModId+":"+name, value);
        nbt.putInt(MnM.ModId+":"+name + "_mod", mod);
        // sync data
        return value;
    }
    public static int getScore(IEntityDataSaver player, String name){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(MnM.ModId+":"+name);
    }
    public static int getMod(IEntityDataSaver player, String name){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(MnM.ModId+":"+name + "_mod");
    }
}
