package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ScarecrowEntity;
import software.bernie.geckolib.model.GeoModel;

public class ScarecrowModel extends GeoModel<ScarecrowEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scarecrow.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scarecrow.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scarecrow.animation.json");

    @Override
    public ResourceLocation getModelResource(ScarecrowEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ScarecrowEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(ScarecrowEntity animatable) {
        return this.animations;
    }
}
