package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ScorpionBabyEntity;
import software.bernie.geckolib.model.GeoModel;

public class ScorpionBabyModel extends GeoModel<ScorpionBabyEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scorpion_baby.geo.json");
    private final ResourceLocation texture_level1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_babies.png");
    private final ResourceLocation texture_level2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_level2_babies.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scorpion_baby.animation.json");
    @Override
    public ResourceLocation getModelResource(ScorpionBabyEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ScorpionBabyEntity entity) {
        if (entity.getVariant() == 2)
            return texture_level2;
        else
            return texture_level1;
    }

    @Override
    public ResourceLocation getAnimationResource(ScorpionBabyEntity animatable) {
        return this.animations;
    }
}
