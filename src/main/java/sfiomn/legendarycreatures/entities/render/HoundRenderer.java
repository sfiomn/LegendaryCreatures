package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.HoundEntity;
import sfiomn.legendarycreatures.entities.render.model.HoundModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class HoundRenderer extends GeoEntityRenderer<HoundEntity> {
    public HoundRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HoundModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(HoundEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
