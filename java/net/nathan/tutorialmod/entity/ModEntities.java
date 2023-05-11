package net.nathan.tutorialmod.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.nathan.tutorialmod.TutorialMod;
import net.nathan.tutorialmod.entity.orc.OrcEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModEntities {
    public static final EntityType<OrcEntity> ORC = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(TutorialMod.MOD_ID, "orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OrcEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.3f)).build());
};