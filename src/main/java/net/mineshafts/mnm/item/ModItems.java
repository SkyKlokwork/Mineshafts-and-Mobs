package net.mineshafts.mnm.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;
import net.minecraft.registry.Registry;
import net.mineshafts.mnm.item.custom.DiceItem;
import net.mineshafts.mnm.item.custom.StaffItem;

public class ModItems {
    public static final ItemGroup MINESHAFTS = ModItemGroup.Mineshafts;

    public static final Item GOLD_COIN = registerItem("gold_coin",
            new Item(new Item.Settings()), MINESHAFTS);
    public static final Item PLAT_COIN = registerItem("platinum_coin",
            new Item(new Item.Settings()), MINESHAFTS);
    public static final Item SILVER_COIN = registerItem("silver_coin",
            new Item(new Item.Settings()), MINESHAFTS);
    public static final Item COPPER_COIN = registerItem("copper_coin",
            new Item(new Item.Settings()), MINESHAFTS);
    public static final Item D20 = registerItem("d20",
            new DiceItem(new Item.Settings().maxCount(5), 20),MINESHAFTS);

    public static final Item STAFF_ITEM = registerItem("staff_item",
            new StaffItem(new Item.Settings().maxCount(1),1,1,1,1),MINESHAFTS);
    public static final Item SLY_STAFF_ITEM = registerItem("sly_staff",
            new StaffItem(new Item.Settings().maxCount(1),1,2,1,1),MINESHAFTS);

    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(MnM.ModId, name), item);
    }

    public static void registerModItems() {
        MnM.LOGGER.debug("Registering Mod Items for " + MnM.ModId);
    }
}
