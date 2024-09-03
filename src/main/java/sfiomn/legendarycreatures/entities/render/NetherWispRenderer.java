package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.WispEntity;
import sfiomn.legendarycreatures.entities.render.model.NetherWispModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.annotation.Nullable;

public class NetherWispRenderer extends GeoEntityRenderer<WispEntity> {
    public NetherWispRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NetherWispModel());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));

        this.shadowRadius = 0.4f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WispEntity instance) {
        return new ResourceLocation(LegendaryCreatures.MOD_ID, "textures/entity/nether_wisp.png");
    }

    @Override
    public RenderType getRenderType(WispEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
