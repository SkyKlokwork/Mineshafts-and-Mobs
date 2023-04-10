package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.mineshafts.mnm.util.IEntityDataSaver;

public class ServerPlayerData {
    public static void setScores(MinecraftServer server, ServerPlayerEntity player,
                                 ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putInt("mnm.strength",buf.readInt());
        nbt.putInt("mnm.dexterity",buf.readInt());
        nbt.putInt("mnm.constitution",buf.readInt());
        nbt.putInt("mnm.intelligence",buf.readInt());
        nbt.putInt("mnm.wisdom",buf.readInt());
        nbt.putInt("mnm.charisma",buf.readInt());
    }
    public static void setRace(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        boolean hasSubRace = buf.readBoolean();
        nbt.putString("mnm.race",buf.readString());
        if (hasSubRace)
            nbt.putString("mnm.subrace",buf.readString());
    }
}
