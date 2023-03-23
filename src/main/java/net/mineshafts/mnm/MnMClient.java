package net.mineshafts.mnm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.mineshafts.mnm.keybinding.MnMKeybinding;
import net.mineshafts.mnm.networking.ModMessages;

public class MnMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MnMKeybinding.register();
        ModMessages.registerS2CPackets();
    }
}
