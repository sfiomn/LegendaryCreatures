package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.MantisEntity;
import sfiomn.legendarycreatures.entities.render.model.MantisModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class MantisRenderer extends GeoEntityRenderer<MantisEntity> {
    public MantisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MantisModel());
    }

    @Override
    public RenderType getRenderType(MantisEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
