package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.PeacockSpiderEntity;
import software.bernie.geckolib.model.GeoModel;

public class PeacockSpiderModel extends GeoModel<PeacockSpiderEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/peacock_spider.geo.json");
    private final ResourceLocation texture_level1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/peacock_spider.png");
    private final ResourceLocation texture_level2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/peacock_spider_level2.png");
    private final ResourceLocation texture_level3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/peacock_spider_level3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/peacock_spider.animation.json");

    @Override
    public ResourceLocation getModelResource(PeacockSpiderEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(PeacockSpiderEntity entity) {
        if (entity.getVariant() == 9)
            return texture_level3;
        else if (entity.getVariant() == 7)
            return texture_level2;
        else
            return texture_level1;
    }

    @Override
    public ResourceLocation getAnimationResource(PeacockSpiderEntity animatable) {
        return this.animations;
    }
}
