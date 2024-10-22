package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.EnderWispPurseEntity;
import software.bernie.geckolib.model.GeoModel;

public class EnderWispPurseModel extends GeoModel<EnderWispPurseEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/wisp_purse.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/ender_wisp.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/wisp_purse.animation.json");

    @Override
    public ResourceLocation getModelResource(EnderWispPurseEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(EnderWispPurseEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(EnderWispPurseEntity animatable) {
        return this.animations;
    }
}
