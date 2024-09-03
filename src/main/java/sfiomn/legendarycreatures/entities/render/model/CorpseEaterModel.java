package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import software.bernie.geckolib.model.GeoModel;

public class CorpseEaterModel extends GeoModel<CorpseEaterEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/corpse_eater.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/corpse_eater.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/corpse_eater.animation.json");

    @Override
    public ResourceLocation getModelResource(CorpseEaterEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(CorpseEaterEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(CorpseEaterEntity animatable) {
        return this.animations;
    }
}
