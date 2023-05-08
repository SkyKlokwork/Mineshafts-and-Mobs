package net.mineshafts.mnm.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.mineshafts.mnm.MnM;
import net.mineshafts.mnm.item.ModItemGroup;

public class ModBlocks {

    public static final Block GOLD_COIN_PILE = registerBlock("gold_coin_pile",
            new RedstonePropertiesBlock(FabricBlockSettings.of(Material.METAL).nonOpaque()), ModItemGroup.Mineshafts);

    public static final Block MAGICORE_BLOCK = registerBlock("magic_ore_block",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool(),
                    UniformIntProvider.create(3, 6)), ModItemGroup.Mineshafts);

    public static final Block DEEPSLATEMAGICORE_BLOCK = registerBlock("deepslate_magic_ore_block",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool(),
                    UniformIntProvider.create(3, 6)), ModItemGroup.Mineshafts);

    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool()), ModItemGroup.Mineshafts);

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registries.BLOCK, new Identifier(MnM.ModId, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.add(block));
        return Registry.register(Registries.ITEM, new Identifier(MnM.ModId, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        MnM.LOGGER.debug("Registering Mod Blocks for " + MnM.ModId);
    }
}
