package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.NetherWispPurseEntity;
import software.bernie.geckolib.model.GeoModel;

public class NetherWispPurseModel extends GeoModel<NetherWispPurseEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/wisp_purse.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/nether_wisp.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/wisp_purse.animation.json");

    @Override
    public ResourceLocation getModelResource(NetherWispPurseEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(NetherWispPurseEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(NetherWispPurseEntity animatable) {
        return this.animations;
    }
}
