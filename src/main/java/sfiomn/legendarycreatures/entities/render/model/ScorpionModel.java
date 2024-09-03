package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ScorpionEntity;
import software.bernie.geckolib.model.GeoModel;

public class ScorpionModel extends GeoModel<ScorpionEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scorpion.geo.json");
    private final ResourceLocation texture_level1 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion.png");
    private final ResourceLocation texture_level2 = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_level2.png");
    private final ResourceLocation texture_level1_babies = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_babies.png");
    private final ResourceLocation texture_level2_babies = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scorpion_level2_babies.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scorpion.animation.json");
    @Override
    public ResourceLocation getModelResource(ScorpionEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ScorpionEntity entity) {
        if (entity.getVariant() == 7)
            return texture_level1_babies;
        else if (entity.getVariant() == 8)
            return texture_level2_babies;
        else if (entity.getVariant() == 2)
            return texture_level2;
        else
            return texture_level1;
    }

    @Override
    public ResourceLocation getAnimationResource(ScorpionEntity animatable) {
        return this.animations;
    }
}
