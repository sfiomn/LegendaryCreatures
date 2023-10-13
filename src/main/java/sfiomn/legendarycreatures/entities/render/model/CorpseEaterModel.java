package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import sfiomn.legendarycreatures.entities.ScarecrowEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CorpseEaterModel extends AnimatedGeoModel<CorpseEaterEntity> {
    @Override
    public ResourceLocation getModelLocation(CorpseEaterEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/corpse_eater.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CorpseEaterEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/corpse_eater.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CorpseEaterEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/corpse_eater.animation.json");
    }
}
