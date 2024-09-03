package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.MojoEntity;
import sfiomn.legendarycreatures.entities.render.model.DesertMojoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DesertMojoRenderer extends GeoEntityRenderer<MojoEntity> {
    public DesertMojoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DesertMojoModel());
        this.shadowRadius = 0.8f;
    }

    // stops the vanilla death animation
    @Override
    protected float getDeathMaxRotation(MojoEntity entityLivingBaseIn) {
        return 0.0F;
    }


    @Override
    public RenderType getRenderType(MojoEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
