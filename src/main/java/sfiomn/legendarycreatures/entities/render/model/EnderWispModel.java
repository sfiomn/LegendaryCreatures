package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.WispEntity;
import software.bernie.geckolib.model.GeoModel;

public class EnderWispModel extends GeoModel<WispEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/wisp.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/ender_wisp.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/wisp.animation.json");

    @Override
    public ResourceLocation getModelResource(WispEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(WispEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(WispEntity animatable) {
        return this.animations;
    }
}
