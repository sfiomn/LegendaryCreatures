package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.BullfrogEntity;
import software.bernie.geckolib.model.GeoModel;

public class BullfrogModel extends GeoModel<BullfrogEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/bullfrog.geo.json");
    private final ResourceLocation texture_level1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/bullfrog.png");
    private final ResourceLocation texture_level2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/bullfrog_level2.png");
    private final ResourceLocation texture_level3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/bullfrog_level3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/bullfrog.animation.json");

    @Override
    public ResourceLocation getModelResource(BullfrogEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(BullfrogEntity entity) {
        if (entity.getVariant() == 9)
            return texture_level3;
        else if (entity.getVariant() == 7)
            return texture_level2;
        else
            return texture_level1;
    }

    @Override
    public ResourceLocation getAnimationResource(BullfrogEntity animatable) {
        return this.animations;
    }
}
