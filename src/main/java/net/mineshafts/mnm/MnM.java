package net.mineshafts.mnm;

import net.fabricmc.api.ModInitializer;
import net.mineshafts.mnm.block.ModBlocks;
import net.mineshafts.mnm.item.ModItems;
import net.mineshafts.mnm.networking.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class MnM implements ModInitializer {
	public static final String ModId = "mnm";
	public static final Logger LOGGER = LoggerFactory.getLogger(ModId);
	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModMessages.registerC2SPackets();
	}
}

