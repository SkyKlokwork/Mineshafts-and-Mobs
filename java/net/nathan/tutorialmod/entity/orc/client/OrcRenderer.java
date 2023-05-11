package net.nathan.tutorialmod.entity.orc.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.nathan.tutorialmod.TutorialMod;
import net.nathan.tutorialmod.entity.orc.OrcEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OrcRenderer extends GeoEntityRenderer<OrcEntity> {

    public OrcRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OrcModel() {
            @Override
            public Identifier getModelLocation(OrcEntity object) {
                return null;
            }

            @Override
            public Identifier getTextureLocation(OrcEntity object) {
                return null;
            }

            @Override
            public Identifier getAnimationFileLocation(OrcEntity animatable) {
                return null;
            }
        });
    }

    @Override
    public Identifier getTextureLocation(OrcEntity instance) {
        return new Identifier(TutorialMod.MOD_ID, "textures/entity/orc/textures/orc.png");
    }
}