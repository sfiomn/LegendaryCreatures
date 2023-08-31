package sfiomn.legendarycreatures.entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import sfiomn.legendarycreatures.entities.model.CorpseEaterModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import javax.annotation.Nullable;

public class CorpseEaterRenderer extends GeoEntityRenderer<CorpseEaterEntity> {
    public CorpseEaterRenderer(EntityRendererManager renderManager) {
        super(renderManager, new CorpseEaterModel());

        addLayer(new LayerGlowingAreasGeo(this,
                entity -> getGeoModelProvider().getTextureLocation((CorpseEaterEntity) entity),
                entity -> getGeoModelProvider().getModelLocation((CorpseEaterEntity) entity),
                resourceLocation -> RenderType.eyes((ResourceLocation) resourceLocation)));

        this.shadowRadius = 0.6f;
    }

    @Override
    public RenderType getRenderType(CorpseEaterEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
