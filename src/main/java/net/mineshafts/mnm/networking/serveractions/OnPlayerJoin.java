package net.mineshafts.mnm.networking.serveractions;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.mineshafts.mnm.util.IEntityDataSaver;

import static net.mineshafts.mnm.networking.ModMessages.SEND_PLAYER_INFO;

public class OnPlayerJoin implements ServerPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        PacketByteBuf packet = PacketByteBufs.create();
        NbtCompound nbt = ((IEntityDataSaver)handler.player).getPersistentData();
        packet.writeNbt(nbt);
        ServerPlayNetworking.send(handler.player, SEND_PLAYER_INFO, packet);
    }
}
