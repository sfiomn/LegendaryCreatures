package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.BullfrogEntity;
import sfiomn.legendarycreatures.entities.PeacockSpiderEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BullfrogModel extends AnimatedGeoModel<BullfrogEntity> {
    private ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/bullfrog.png");

    @Override
    public ResourceLocation getModelLocation(BullfrogEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/bullfrog.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BullfrogEntity object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BullfrogEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/bullfrog.animation.json");
    }

    @Override
    public void setCustomAnimations(BullfrogEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);

        if (entity.getVariant() == 9)
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/bullfrog_level3.png");
        else if (entity.getVariant() == 7)
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/bullfrog_level2.png");
        else
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/bullfrog.png");
    }
}
