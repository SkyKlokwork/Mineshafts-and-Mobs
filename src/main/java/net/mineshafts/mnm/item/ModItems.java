package net.mineshafts.mnm.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item GOLD_COIN = registerItem("gold_coin",
            new Item(new Item.Settings()), ModItemGroup.Mineshafts);


    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(MnM.ModId, name), item);
    }

    public static void registerModItems() {
        MnM.LOGGER.debug("Registering Mod Items for " + MnM.ModId);
    }
}
