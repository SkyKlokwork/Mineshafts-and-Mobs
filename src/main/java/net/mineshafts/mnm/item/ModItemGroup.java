package net.mineshafts.mnm.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroupBuilderImpl;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;

public class ModItemGroup {
    public static final ItemGroup Mineshafts = FabricItemGroup.builder(new Identifier(MnM.ModId))
            .displayName(Text.literal("Mineshafts & Mobs")).icon(()->new ItemStack(ModItems.GOLD_COIN)).build();
}
