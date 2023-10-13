package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.HoundEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HoundModel extends AnimatedGeoModel<HoundEntity> {
    @Override
    public ResourceLocation getModelLocation(HoundEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/hound.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(HoundEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/hound.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(HoundEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/hound.animation.json");
    }
}
