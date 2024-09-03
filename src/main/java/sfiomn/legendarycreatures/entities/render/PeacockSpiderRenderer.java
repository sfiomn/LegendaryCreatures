package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.PeacockSpiderEntity;
import sfiomn.legendarycreatures.entities.render.model.PeacockSpiderModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PeacockSpiderRenderer extends GeoEntityRenderer<PeacockSpiderEntity> {
    public PeacockSpiderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PeacockSpiderModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(PeacockSpiderEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
