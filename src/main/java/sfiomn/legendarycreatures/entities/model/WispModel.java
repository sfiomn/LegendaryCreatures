package sfiomn.legendarycreatures.entities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.WispEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WispModel extends AnimatedGeoModel<WispEntity> {
    @Override
    public ResourceLocation getModelLocation(WispEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/wisp.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WispEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/wisp.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WispEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/wisp.animation.json");
    }
}
