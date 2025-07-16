package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.LadybugEntity;
import sfiomn.legendarycreatures.entities.render.model.LadybugModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class LadybugRenderer extends GeoEntityRenderer<LadybugEntity> {
    public LadybugRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LadybugModel());
    }

    @Override
    public RenderType getRenderType(LadybugEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
