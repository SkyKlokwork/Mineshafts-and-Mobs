package net.mineshafts.mnm.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.function.Consumer;

public class ExampleC2SPacket {
//    Example of a packet we send from the client to the server
//everything in this packet is happening on the server since it's a packet to the server from the client
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender response) {
        EntityType.COW.spawn((ServerWorld) player.world, player.getBlockPos(), SpawnReason.TRIGGERED);
    }
}
