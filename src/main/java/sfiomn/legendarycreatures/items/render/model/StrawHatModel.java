package sfiomn.legendarycreatures.items.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.items.StrawHatItem;
import software.bernie.geckolib.model.GeoModel;

public class StrawHatModel extends GeoModel<StrawHatItem> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/straw_hat.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/armor/straw_hat.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/straw_hat.animation.json");
    @Override
    public ResourceLocation getModelResource(StrawHatItem strawHatItem) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(StrawHatItem strawHatItem) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(StrawHatItem strawHatItem) {
        return animations;
    }
}
