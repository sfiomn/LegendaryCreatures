package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.HermitCrabEntity;
import sfiomn.legendarycreatures.entities.render.model.HermitCrabModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class HermitCrabRenderer extends GeoEntityRenderer<HermitCrabEntity> {
    public HermitCrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HermitCrabModel());
    }

    @Override
    public RenderType getRenderType(HermitCrabEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
