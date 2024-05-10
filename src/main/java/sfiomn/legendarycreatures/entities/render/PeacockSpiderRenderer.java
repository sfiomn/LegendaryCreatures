package sfiomn.legendarycreatures.entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.entities.PeacockSpiderEntity;
import sfiomn.legendarycreatures.entities.ScorpionEntity;
import sfiomn.legendarycreatures.entities.render.model.PeacockSpiderModel;
import sfiomn.legendarycreatures.entities.render.model.ScorpionModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PeacockSpiderRenderer extends GeoEntityRenderer<PeacockSpiderEntity> {
    public PeacockSpiderRenderer(EntityRendererManager renderManager) {
        super(renderManager, new PeacockSpiderModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(PeacockSpiderEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
