package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.EnderWispEntity;
import sfiomn.legendarycreatures.entities.render.model.EnderWispModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.annotation.Nullable;

public class EnderWispRenderer extends GeoEntityRenderer<EnderWispEntity> {
    public EnderWispRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EnderWispModel());
        this.withScale(0.8f);

        addRenderLayer(new AutoGlowingGeoLayer<>(this));

        this.shadowRadius = 0.4f;
    }

    @Override
    public RenderType getRenderType(EnderWispEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
