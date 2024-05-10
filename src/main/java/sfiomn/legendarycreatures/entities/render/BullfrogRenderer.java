package sfiomn.legendarycreatures.entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.entities.BullfrogEntity;
import sfiomn.legendarycreatures.entities.PeacockSpiderEntity;
import sfiomn.legendarycreatures.entities.render.model.BullfrogModel;
import sfiomn.legendarycreatures.entities.render.model.PeacockSpiderModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BullfrogRenderer extends GeoEntityRenderer<BullfrogEntity> {
    public BullfrogRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BullfrogModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public RenderType getRenderType(BullfrogEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
