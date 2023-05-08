package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

import static net.mineshafts.mnm.networking.ModMessages.CHANGE_SPELL;


public class SpellCycle {

    static List<Integer> spells_local = new ArrayList<>();
    static int current_local;
    public static void getSpellData(int[] spells, int current){
        for (int i:spells) {
           spells_local.add(i);
        }
        current_local = current;
    }


    public static void next_spell() {

        if(current_local < spells_local.size()) {
            current_local++;
        }
        else {
            current_local = 1;
        }
        sendCyclePacket(current_local);

    }

    public static void prev_spell() {
        if (current_local > spells_local.size()) {
            current_local = spells_local.size();
        }
        if (current_local <= 1)
            current_local = spells_local.size();
        else
            current_local--;
        sendCyclePacket(current_local);

    }

    public static void sendCyclePacket(int current_local) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(current_local);
        ClientPlayNetworking.send(CHANGE_SPELL,buf);
    }

    public static String get_current() {
        return String.valueOf(current_local);
    }

    public static void reset_current() {
        current_local = 1;
    }
    public static String get_listData() {
        StringBuilder spells_list = new StringBuilder();
        for (int thing: spells_local) {
            spells_list.append(thing + " ");
        }
        spells_list.append(spells_local.size() + " ");
        return spells_list.toString();

    }

}
