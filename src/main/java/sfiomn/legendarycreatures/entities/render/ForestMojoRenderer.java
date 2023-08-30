package sfiomn.legendarycreatures.entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.entities.MojoEntity;
import sfiomn.legendarycreatures.entities.model.ForestMojoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ForestMojoRenderer extends GeoEntityRenderer<MojoEntity> {
    public ForestMojoRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ForestMojoModel());
        this.shadowRadius = 0.8f;
    }

    // stops the vanilla death animation
    @Override
    protected float getDeathMaxRotation(MojoEntity entityLivingBaseIn) {
        return 0.0F;
    }

    @Override
    public RenderType getRenderType(MojoEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
