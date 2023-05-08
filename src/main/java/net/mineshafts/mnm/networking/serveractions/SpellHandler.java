package net.mineshafts.mnm.networking.serveractions;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
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
        } else {
            //TODO: figure out why I can't put just one number in
            for (int i : nbt.getIntArray("mnm.known_spells")) {
                temp.add(i);
            }
            if(!/**added to test**/validSpell(packet.readInt())){
               if (!temp.contains(packet.readInt()))
                    temp.add(packet.readInt());
            }
        }
        nbt.putIntArray("mnm.known_spells", temp);

    }
}
