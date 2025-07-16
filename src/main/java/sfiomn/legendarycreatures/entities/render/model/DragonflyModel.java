package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.DragonflyEntity;
import software.bernie.geckolib.model.GeoModel;

public class DragonflyModel extends GeoModel<DragonflyEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/dragonfly.geo.json");
    private final ResourceLocation texture1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/dragonfly.png");
    private final ResourceLocation texture2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/dragonfly2.png");
    private final ResourceLocation texture3 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/dragonfly3.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/dragonfly.animation.json");

    @Override
    public ResourceLocation getModelResource(DragonflyEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(DragonflyEntity entity) {
        if (entity.getVariant() == 1)
            return texture1;
        else if (entity.getVariant() == 2)
            return texture2;
        else
            return texture3;
    }

    @Override
    public ResourceLocation getAnimationResource(DragonflyEntity animatable) {
        return this.animations;
    }
}
