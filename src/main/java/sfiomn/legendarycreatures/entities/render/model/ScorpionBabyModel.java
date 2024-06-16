package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ScorpionBabyEntity;
import sfiomn.legendarycreatures.entities.ScorpionEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorpionBabyModel extends AnimatedGeoModel<ScorpionBabyEntity> {
    private ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion.png");
    @Override
    public ResourceLocation getModelLocation(ScorpionBabyEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scorpion_baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ScorpionBabyEntity object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ScorpionBabyEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scorpion_baby.animation.json");
    }

    @Override
    public void setCustomAnimations(ScorpionBabyEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);

        if (entity.getVariant() == 2)
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_level2_babies.png");
        else
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_babies.png");
    }
}
