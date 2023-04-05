package net.mineshafts.mnm.networking.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.mineshafts.mnm.networking.ModMessages;
import net.mineshafts.mnm.util.AbilityScoreData;
import net.mineshafts.mnm.util.IEntityDataSaver;

public class AbilityScoresC2SPacket {
    public static void addStrength(MinecraftServer server, ServerPlayerEntity player,
                                   ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        // Server-side code
        AbilityScoreData.addScore(((IEntityDataSaver)player), "strength", buf.readInt());
    }
    public static void setStrength(MinecraftServer server, ServerPlayerEntity player,
                                   ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        // Server-side code
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        player.sendMessageToClient(Text.of(String.valueOf(nbt)), false);
        AbilityScoreData.setScore(((IEntityDataSaver)player), "strength", buf.readInt());
    }
    public static void sendStrength(MinecraftServer server, ServerPlayerEntity player,
                                   ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        // Server-side code
        PacketByteBuf buff = PacketByteBufs.create();
        buff.writeInt(AbilityScoreData.getScore((IEntityDataSaver)player, "mnm:strength"));
        ServerPlayNetworking.send(player, ModMessages.STRENGTH_GET, buff);
    }
    public static void setDexterity(MinecraftServer server, ServerPlayerEntity player,
                                    ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        // Server-side code
        AbilityScoreData.setScore(((IEntityDataSaver)player), "dexterity", buf.readInt());
    }
}
