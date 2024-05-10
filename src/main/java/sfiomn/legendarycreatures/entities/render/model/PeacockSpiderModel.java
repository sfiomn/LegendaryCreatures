package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.PeacockSpiderEntity;
import sfiomn.legendarycreatures.entities.ScorpionEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PeacockSpiderModel extends AnimatedGeoModel<PeacockSpiderEntity> {
    private ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/peacock_spider.png");

    @Override
    public ResourceLocation getModelLocation(PeacockSpiderEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/peacock_spider.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PeacockSpiderEntity object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PeacockSpiderEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/peacock_spider.animation.json");
    }

    @Override
    public void setCustomAnimations(PeacockSpiderEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);

        if (entity.getVariant() == 9)
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/peacock_spider_level3.png");
        else if (entity.getVariant() == 7)
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/peacock_spider_level3.png");
        else
            texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/peacock_spider.png");
    }
}
