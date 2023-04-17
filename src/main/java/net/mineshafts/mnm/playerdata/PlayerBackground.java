package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.BackgroundEnum;

import static net.mineshafts.mnm.networking.ModMessages.SET_BACKGROUND;

public class PlayerBackground {
    public static boolean existingBackground = false;
    private static BackgroundEnum background;

    public static BackgroundEnum getBackground() {
        return background;
    }
    public static void setBackground(BackgroundEnum background) {
        PlayerBackground.background = background;
        existingBackground = true;
    }
    public static void setBackground(){
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeString(background.getTranslationKey());
        ClientPlayNetworking.send(SET_BACKGROUND, packet);
    }
}
