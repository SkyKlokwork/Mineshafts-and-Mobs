package net.nathan.tutorialmod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.kaupenjoe.tutorialmod.item.ModItemGroup;
import net.kaupenjoe.tutorialmod.item.custom.DataTabletItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.nathan.tutorialmod.entity.ModEntities;

import static software.bernie.example.registry.RegistryUtils.registerItem;

public class ModItems {
    public static final Item ORC_SPAWN_EGG = registerItem("orc_spawn_egg",
            new SpawnEggItem(ModEntities.ORC, 0x228B22, 0XC19A6B,
                    new FabricItemSettings().group(ModItemGroup.MYTHRIL).maxCount(1)));
}
