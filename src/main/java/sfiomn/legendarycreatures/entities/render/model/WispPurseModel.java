package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.WispPurseEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WispPurseModel extends AnimatedGeoModel<WispPurseEntity> {
    @Override
    public ResourceLocation getModelLocation(WispPurseEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/wisp_purse.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WispPurseEntity object) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/wisp.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WispPurseEntity animatable) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/wisp_purse.animation.json");
    }
}
