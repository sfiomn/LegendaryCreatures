package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.MantisEntity;
import software.bernie.geckolib.model.GeoModel;

public class MantisModel extends GeoModel<MantisEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/mantis.geo.json");
    private final ResourceLocation texture1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/mantis.png");
    private final ResourceLocation texture2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/mantis2.png");
    private final ResourceLocation texture3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/mantis3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/mantis.animation.json");

    @Override
    public ResourceLocation getModelResource(MantisEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(MantisEntity entity) {
        if (entity.getVariant() == 1)
            return texture1;
        else if (entity.getVariant() == 2)
            return texture2;
        else
            return texture3;
    }

    @Override
    public ResourceLocation getAnimationResource(MantisEntity animatable) {
        return this.animations;
    }
}
