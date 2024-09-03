package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.MojoEntity;
import software.bernie.geckolib.model.GeoModel;

public class ForestMojoModel extends GeoModel<MojoEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/forest_mojo.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/mojo.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/forest_mojo.animation.json");

    @Override
    public ResourceLocation getModelResource(MojoEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(MojoEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(MojoEntity animatable) {
        return this.animations;
    }
}
