package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.ScarecrowEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScarecrowModel extends AnimatedGeoModel<ScarecrowEntity> {
    @Override
    public ResourceLocation getModelLocation(ScarecrowEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scarecrow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ScarecrowEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/scarecrow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ScarecrowEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scarecrow.animation.json");
    }
}
