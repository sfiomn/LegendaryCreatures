package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ScarabEntity;
import software.bernie.geckolib.model.GeoModel;

public class ScarabModel extends GeoModel<ScarabEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scarab.geo.json");
    private final ResourceLocation texture1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scarab.png");
    private final ResourceLocation texture2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scarab2.png");
    private final ResourceLocation texture3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scarab3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scarab.animation.json");

    @Override
    public ResourceLocation getModelResource(ScarabEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ScarabEntity entity) {
        if (entity.getVariant() == 1)
            return texture1;
        else if (entity.getVariant() == 2)
            return texture2;
        else
            return texture3;
    }

    @Override
    public ResourceLocation getAnimationResource(ScarabEntity animatable) {
        return this.animations;
    }
}
