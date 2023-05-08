package net.mineshafts.mnm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.render.RenderLayer;
import net.mineshafts.mnm.keybinding.MnMKeybinding;
import net.mineshafts.mnm.networking.ModMessages;

import static net.mineshafts.mnm.block.ModBlocks.GOLD_COIN_PILE;

public class MnMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MnMKeybinding.register();
        ModMessages.registerS2CPackets();
        BlockRenderLayerMap.INSTANCE.putBlock(GOLD_COIN_PILE, RenderLayer.getCutout());
    }
}
