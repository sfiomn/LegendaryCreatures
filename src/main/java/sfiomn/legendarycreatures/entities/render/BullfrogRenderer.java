package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.BullfrogEntity;
import sfiomn.legendarycreatures.entities.render.model.BullfrogModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BullfrogRenderer extends GeoEntityRenderer<BullfrogEntity> {
    public BullfrogRenderer(EntityRendererProvider.Context  renderManager) {
        super(renderManager, new BullfrogModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public RenderType getRenderType(BullfrogEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
