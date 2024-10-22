package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.NetherWispEntity;
import sfiomn.legendarycreatures.entities.render.model.NetherWispModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.annotation.Nullable;

public class NetherWispRenderer extends GeoEntityRenderer<NetherWispEntity> {
    public NetherWispRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NetherWispModel());
        this.withScale(0.8f);

        addRenderLayer(new AutoGlowingGeoLayer<>(this));

        this.shadowRadius = 0.4f;
    }

    @Override
    public RenderType getRenderType(NetherWispEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
