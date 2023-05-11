package net.kaupenjoe.tutorialmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.entity.custom.OrcEntity;
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
}