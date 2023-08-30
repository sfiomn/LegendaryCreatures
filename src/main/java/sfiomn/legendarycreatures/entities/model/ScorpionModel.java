package sfiomn.legendarycreatures.entities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.MojoEntity;
import sfiomn.legendarycreatures.entities.ScorpionEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorpionModel extends AnimatedGeoModel<ScorpionEntity> {
    private ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion.png");
    @Override
    public ResourceLocation getModelLocation(ScorpionEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scorpion.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ScorpionEntity object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ScorpionEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scorpion.animation.json");
    }

    @Override
    public void setCustomAnimations(ScorpionEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);

        if (entity.hasBabies())
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/scorpion_babies.png");
        else
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion.png");
    }
}
