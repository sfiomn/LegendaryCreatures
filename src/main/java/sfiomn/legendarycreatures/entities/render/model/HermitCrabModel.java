package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.HermitCrabEntity;
import software.bernie.geckolib.model.GeoModel;

public class HermitCrabModel extends GeoModel<HermitCrabEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/hermit_crab.geo.json");
    private final ResourceLocation texture1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/hermit_crab.png");
    private final ResourceLocation texture2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/hermit_crab2.png");
    private final ResourceLocation texture3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/hermit_crab3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/hermit_crab.animation.json");

    @Override
    public ResourceLocation getModelResource(HermitCrabEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(HermitCrabEntity entity) {
        if (entity.getVariant() == 1)
            return texture1;
        else if (entity.getVariant() == 2)
            return texture2;
        else
            return texture3;
    }

    @Override
    public ResourceLocation getAnimationResource(HermitCrabEntity animatable) {
        return this.animations;
    }
}
