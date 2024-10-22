package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import sfiomn.legendarycreatures.entities.EnderWispPurseEntity;
import sfiomn.legendarycreatures.entities.render.model.EnderWispPurseModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.annotation.Nullable;

public class EnderWispPurseRenderer extends GeoEntityRenderer<EnderWispPurseEntity> {

    public EnderWispPurseRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EnderWispPurseModel());
        this.withScale(0.8f);

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public RenderType getRenderType(EnderWispPurseEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
