package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.ScorpionEntity;
import sfiomn.legendarycreatures.entities.render.model.ScorpionModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ScorpionRenderer extends GeoEntityRenderer<ScorpionEntity> {
    public ScorpionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScorpionModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public RenderType getRenderType(ScorpionEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
