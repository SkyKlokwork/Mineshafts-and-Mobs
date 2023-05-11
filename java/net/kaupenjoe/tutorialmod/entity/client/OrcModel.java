package net.kaupenjoe.tutorialmod.entity.client;

import net.minecraft.util.Identifier;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.entity.custom.OrcEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class OrcModel extends AnimatedGeoModel<OrcEntity> {

    @Override
    public Identifier getModelLocation(OrcEntity object) {
        return new Identifier(TutorialMod.MOD_ID, "geo/orc.geo.json");
    }

    @Override
    public Identifier getTextureLocation(OrcEntity object) {
        return new Identifier(TutorialMod.MOD_ID, "textures/entity/orc/orc.png");
    }

    @Override
    public Identifier getAnimationFileLocation(OrcEntity animatable) {
        return new Identifier(TutorialMod.MOD_ID, "animations/orc.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(OrcEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}