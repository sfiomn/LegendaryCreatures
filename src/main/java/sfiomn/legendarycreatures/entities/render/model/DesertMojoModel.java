package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.DesertMojoEntity;
import sfiomn.legendarycreatures.entities.MojoEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DesertMojoModel extends AnimatedGeoModel<MojoEntity> {

    @Override
    public ResourceLocation getModelLocation(MojoEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID,"geo/desert_mojo.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MojoEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/mojo.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MojoEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID,"animations/desert_mojo.animation.json");
    }
}
