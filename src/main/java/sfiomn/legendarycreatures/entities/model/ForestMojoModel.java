package sfiomn.legendarycreatures.entities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ForestMojoEntity;
import sfiomn.legendarycreatures.entities.MojoEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ForestMojoModel extends AnimatedGeoModel<MojoEntity> {
    @Override
    public ResourceLocation getModelLocation(MojoEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID,"geo/forest_mojo.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MojoEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/mojo.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MojoEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID,"animations/forest_mojo.animation.json");
    }
}
