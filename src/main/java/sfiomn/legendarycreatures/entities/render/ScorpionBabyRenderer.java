package sfiomn.legendarycreatures.entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import sfiomn.legendarycreatures.entities.ScorpionBabyEntity;
import sfiomn.legendarycreatures.entities.model.ScorpionBabyModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import javax.annotation.Nullable;

public class ScorpionBabyRenderer extends GeoEntityRenderer<ScorpionBabyEntity> {

    private static final AxisAlignedBB INITIAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public ScorpionBabyRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ScorpionBabyModel());

        addLayer(new LayerGlowingAreasGeo(this,
                entity -> getGeoModelProvider().getTextureLocation((ScorpionBabyEntity) entity),
                entity -> getGeoModelProvider().getModelLocation((ScorpionBabyEntity) entity),
                resourceLocation -> RenderType.eyes((ResourceLocation) resourceLocation)));

        this.shadowRadius = 0.0f;
    }

    @Override
    public RenderType getRenderType(ScorpionBabyEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
