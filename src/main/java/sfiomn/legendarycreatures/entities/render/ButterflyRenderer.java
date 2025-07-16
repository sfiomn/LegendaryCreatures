package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.ButterflyEntity;
import sfiomn.legendarycreatures.entities.render.model.ButterflyModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {
    public ButterflyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ButterflyModel());
    }

    @Override
    public RenderType getRenderType(ButterflyEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
