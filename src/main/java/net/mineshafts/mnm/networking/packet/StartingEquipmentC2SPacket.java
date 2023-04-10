package net.mineshafts.mnm.networking.packet;

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
import net.mineshafts.mnm.networking.serveractions.ItemGiver;
import net.mineshafts.mnm.util.AbilityScoreData;
import net.mineshafts.mnm.util.IEntityDataSaver;

public class StartingEquipmentC2SPacket {
    public static void giveItems(MinecraftServer server, ServerPlayerEntity player,
                                   ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        // Server-side code
        int count = buf.readInt();
        for (int i=0;i<count;i++)
            ItemGiver.giveItem(player, buf.readItemStack());
        ItemGiver.setGotten(player, "hasStartingEquipment", true);
    }

    public static void resetNBT(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        for (String key:nbt.getKeys())
            nbt.remove(key);
    }
}
