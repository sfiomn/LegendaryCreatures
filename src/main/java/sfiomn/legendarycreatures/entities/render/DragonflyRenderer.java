package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.DragonflyEntity;
import sfiomn.legendarycreatures.entities.render.model.DragonflyModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DragonflyRenderer extends GeoEntityRenderer<DragonflyEntity> {
    public DragonflyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonflyModel());
    }

    @Override
    public RenderType getRenderType(DragonflyEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
