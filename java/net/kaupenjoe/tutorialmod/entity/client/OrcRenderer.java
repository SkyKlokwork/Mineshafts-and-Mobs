package net.kaupenjoe.tutorialmod.entity.client;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.entity.custom.OrcEntity;
import net.kaupenjoe.tutorialmod.entity.client.OrcModel;
import net.kaupenjoe.tutorialmod.entity.custom.RaccoonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OrcRenderer extends GeoEntityRenderer<OrcEntity> {
    public OrcRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OrcModel());
    }

    @Override
    public Identifier getTextureLocation(OrcEntity instance) {
        return new Identifier(TutorialMod.MOD_ID, "textures/entity/orc/orc.png");
    }
}