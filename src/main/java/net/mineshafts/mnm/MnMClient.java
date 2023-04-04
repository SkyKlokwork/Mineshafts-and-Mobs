package net.mineshafts.mnm;

import net.fabricmc.api.ClientModInitializer;
import net.mineshafts.mnm.networking.ModMessages;

public class MnMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerC2SPacket();
    }
}
