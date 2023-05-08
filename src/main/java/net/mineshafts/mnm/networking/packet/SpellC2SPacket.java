package net.mineshafts.mnm.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.mineshafts.mnm.networking.serveractions.SpellHandler;
import net.mineshafts.mnm.util.IEntityDataSaver;

import static net.mineshafts.mnm.networking.ModMessages.SEND_PLAYER_INFO;

public class SpellC2SPacket {
    public static void AddSpells(MinecraftServer server, ServerPlayerEntity player,
                             ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        SpellHandler.addSpells(player, buf);

        //TODO: figure out best way to get new data to SpellCycle
        PacketByteBuf packet = PacketByteBufs.create();
        NbtCompound nbt = ((IEntityDataSaver)handler.player).getPersistentData();
        packet.writeNbt(nbt);
        ServerPlayNetworking.send(handler.player, SEND_PLAYER_INFO, packet);
    }
    public static void CastSpell(MinecraftServer server, ServerPlayerEntity player,
                                 ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        SpellHandler.castSpell(player);
    }
}