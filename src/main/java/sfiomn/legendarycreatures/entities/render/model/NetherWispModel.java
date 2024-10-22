package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.NetherWispEntity;
import software.bernie.geckolib.model.GeoModel;

public class NetherWispModel extends GeoModel<NetherWispEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/wisp.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/nether_wisp.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/wisp.animation.json");

    @Override
    public ResourceLocation getModelResource(NetherWispEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(NetherWispEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(NetherWispEntity animatable) {
        return this.animations;
    }
}
