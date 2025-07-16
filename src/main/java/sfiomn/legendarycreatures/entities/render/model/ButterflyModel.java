package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.BullfrogEntity;
import sfiomn.legendarycreatures.entities.ButterflyEntity;
import software.bernie.geckolib.model.GeoModel;

public class ButterflyModel extends GeoModel<ButterflyEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/butterfly.geo.json");
    private final ResourceLocation texture1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/butterfly.png");
    private final ResourceLocation texture2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/butterfly2.png");
    private final ResourceLocation texture3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/butterfly3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/butterfly.animation.json");

    @Override
    public ResourceLocation getModelResource(ButterflyEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ButterflyEntity entity) {
        if (entity.getVariant() == 1)
            return texture1;
        else if (entity.getVariant() == 2)
            return texture2;
        else
            return texture3;
    }

    @Override
    public ResourceLocation getAnimationResource(ButterflyEntity animatable) {
        return this.animations;
    }
}
