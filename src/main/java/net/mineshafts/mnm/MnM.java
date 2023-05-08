package net.mineshafts.mnm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.mineshafts.mnm.block.ModBlocks;
import net.mineshafts.mnm.item.ModItems;
import net.mineshafts.mnm.networking.ModMessages;
import net.mineshafts.mnm.networking.serveractions.OnPlayerJoin;
import net.mineshafts.mnm.networking.serveractions.SpellHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MnM implements ModInitializer {
	public static final String ModId = "mnm";
	public static final Logger LOGGER = LoggerFactory.getLogger(ModId);
	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModMessages.registerC2SPackets();
		SpellHandler.populateSpells();

		ServerPlayConnectionEvents.INIT.register(new OnPlayerJoin());
	}
}

