package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import sfiomn.legendarycreatures.entities.render.layer.CorpseEaterEmissiveLayer;
import sfiomn.legendarycreatures.entities.render.model.CorpseEaterModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.annotation.Nullable;

public class CorpseEaterRenderer extends GeoEntityRenderer<CorpseEaterEntity> {
    public CorpseEaterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CorpseEaterModel());
        this.withScale(0.8f);

        if (LegendaryCreatures.oculusLoaded)
            addRenderLayer(new CorpseEaterEmissiveLayer(this));
        else
            addRenderLayer(new AutoGlowingGeoLayer<>(this));

        this.shadowRadius = 0.4f;
    }

    @Override
    public RenderType getRenderType(CorpseEaterEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
