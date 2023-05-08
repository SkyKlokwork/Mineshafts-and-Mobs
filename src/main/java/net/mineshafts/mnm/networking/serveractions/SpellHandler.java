package net.mineshafts.mnm.networking.serveractions;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.mineshafts.mnm.util.IEntityDataSaver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpellHandler {

    static HashMap<Integer/** would be ID**/, String /**Maybe could be swapped for an Object?**/> validSpells = new HashMap<>();

    public static void populateSpells() {
        validSpells.clear();
        validSpells.put(1,"test 1");
        validSpells.put(12,"test 2");
        validSpells.put(13,"test 3");
        validSpells.put(9,"test 4");
    }


    public static boolean validSpell(int input) {
        return validSpells.containsKey(input);
    }

    public static String retrieveSpell(int input) {
        return validSpells.get(input);
    }


    public static void addSpells(ServerPlayerEntity player, PacketByteBuf packet){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        List<Integer> temp = new ArrayList<Integer>();
        if (packet.hasArray()) {
            for (int i : nbt.getIntArray("mnm.known_spells")) {
                temp.add(i);
            }
            for (int i : packet.readIntArray()) {
                if(!validSpell(i))
                    continue;
                if (!temp.contains(i))
                    temp.add(i);
            }
        }
        nbt.putIntArray("mnm.known_spells", temp);
    }
    public static void castSpell(ServerPlayerEntity player) {
        NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
        int[] list = nbt.getIntArray("mnm.known_spells");
        int current = nbt.getInt("mnm.current_spell");
        if (list.length == 0) {
            player.sendMessage(Text.literal("No spell"));
        }
        if (current==0) current= 1;
        else
        switch (list[current - 1]) {
            case 1: player.sendMessage(Text.literal("Spell 1"));
                break;
            case 13: player.sendMessage(Text.literal("Spell 2"));
                break;
            case 12: player.sendMessage(Text.literal("Spell 3"));
                break;
            case 9: player.sendMessage(Text.literal("Spell 4"));
                break;
        }
    }
}
