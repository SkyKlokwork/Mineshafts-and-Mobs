package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.mineshafts.mnm.util.IEntityDataSaver;

import java.util.Set;

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

    public static void setProficiencies(MinecraftServer server, ServerPlayerEntity player,
                                        ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        setNbt(player,buf,"mnm.proficiencies");
    }

    public static void setClass(MinecraftServer server, ServerPlayerEntity player,
                                        ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        setNbt(player,buf,"mnm.classes");
    }
    public static void setBackground(MinecraftServer server, ServerPlayerEntity player,
                                     ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putString("mnm.background",buf.readString());
    }
    public static void changeSpell(MinecraftServer server, ServerPlayerEntity player,
                                   ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putInt("mnm.current_spell",buf.readInt());
        player.sendMessage(Text.literal(String.valueOf(nbt.getInt("mnm.current_spell"))));
        player.sendMessage(Text.literal(SpellCycle.get_listData()));
    }
    public static void castSpell(MinecraftServer server, ServerPlayerEntity player,
                                 ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
    }

    private static void setNbt(ServerPlayerEntity player, PacketByteBuf buf, String key){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound value = buf.readNbt();
        nbt.put(key,value);
    }

    public static void broadcastNbt(MinecraftServer server, ServerPlayerEntity player,
                                    ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        player.sendMessage(Text.of(nbtString(nbt)));
//        server.sendMessage(Text.of(nbt.toString()));
    }

    private static String nbtString(NbtCompound nbt){
        StringBuilder output = new StringBuilder("{");
        Set<String> keys = nbt.getKeys();
        for (String key:keys){
            output.append(key).append(':');
            NbtElement element = nbt.get(key);
            if (element==null)
                output.append("null");
            else
                output.append(element);
            output.append(',');
        }
        if (keys.size()>0)
            output.deleteCharAt(output.length()-1);
        output.append('}');
        return output.toString();
    }
}
