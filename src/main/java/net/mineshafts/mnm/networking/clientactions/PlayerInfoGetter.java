package net.mineshafts.mnm.networking.clientactions;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.BackgroundEnum;
import net.mineshafts.mnm.playerdata.*;

public class PlayerInfoGetter {
    public static void getPlayerInfo(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        NbtCompound nbt = buf.readNbt();
        if (nbt == null)
            return;
        if (nbt.contains("mnm.strength")) {
            int[] scores = new int[]{nbt.getInt("mnm.strength"), nbt.getInt("mnm.dexterity"), nbt.getInt("mnm.constitution"),
                    nbt.getInt("mnm.wisdom"), nbt.getInt("mnm.intelligence"), nbt.getInt("mnm.charisma")};
            PlayerAbilityScores.setScores(scores);
        }
        if (nbt.contains("mnm.race")) {
            PlayerRace.setRace(nbt.getString("mnm.race"));
            if (nbt.contains("mnm.subrace"))
                PlayerRace.setSubRace(nbt.getString("mnm.subrace"));
        }
        if (nbt.contains("mnm.class")) {
            NbtCompound classes = nbt.getCompound("mnm.class");
            PlayerClass.getClasses(classes);
        }
        if (nbt.contains("mnm.background")) {
            PlayerBackground.setBackground(BackgroundEnum.SgetEnum(nbt.getString("mnm.background")));
        }
        if (nbt.contains("mnm.proficiencies")) {
            Proficiencies.getProficiencies(nbt.getCompound("mnm.proficiencies"));
        }
        // Put future Player Info types here
    }
}
