package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.HoundEntity;
import software.bernie.geckolib.model.GeoModel;

public class HoundModel extends GeoModel<HoundEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/hound.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/hound.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/hound.animation.json");

    @Override
    public ResourceLocation getModelResource(HoundEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(HoundEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(HoundEntity animatable) {
        return this.animations;
    }
}
