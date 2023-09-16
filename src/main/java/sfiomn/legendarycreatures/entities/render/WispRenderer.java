package sfiomn.legendarycreatures.entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import sfiomn.legendarycreatures.entities.WispEntity;
import sfiomn.legendarycreatures.entities.model.WispModel;
import sfiomn.legendarycreatures.util.RenderTypeUtil;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import javax.annotation.Nullable;

public class WispRenderer extends GeoEntityRenderer<WispEntity> {
    public WispRenderer(EntityRendererManager renderManager) {
        super(renderManager, new WispModel());

        addLayer(new LayerGlowingAreasGeo(this,
                entity -> getGeoModelProvider().getTextureLocation((WispEntity) entity),
                entity -> getGeoModelProvider().getModelLocation((WispEntity) entity),
                resourceLocation -> RenderTypeUtil.glowingNoTransparency((ResourceLocation) resourceLocation)));

        this.shadowRadius = 0.4f;
    }

    @Override
    public ResourceLocation getTextureLocation(WispEntity instance) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/wisp.png");
    }

    @Override
    public RenderType getRenderType(WispEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
