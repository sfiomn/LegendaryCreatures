package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.ScarecrowEntity;
import sfiomn.legendarycreatures.entities.render.model.ScarecrowModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ScarecrowRenderer extends GeoEntityRenderer<ScarecrowEntity> {
    public ScarecrowRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScarecrowModel());
        this.withScale(0.8f);
        this.shadowRadius = 0.8f;
    }

    @Override
    public RenderType getRenderType(ScarecrowEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
