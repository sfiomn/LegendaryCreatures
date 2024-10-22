package sfiomn.legendarycreatures.entities.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class CorpseEaterEmissiveLayer extends GeoRenderLayer<CorpseEaterEntity> {

    public static ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/corpse_eater_glowmask.png");

    public CorpseEaterEmissiveLayer(GeoRenderer<CorpseEaterEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(PoseStack poseStack, CorpseEaterEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType glowRenderType = RenderType.entityTranslucentEmissive(EMISSIVE_TEXTURE);
        this.getRenderer().reRender(this.getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
