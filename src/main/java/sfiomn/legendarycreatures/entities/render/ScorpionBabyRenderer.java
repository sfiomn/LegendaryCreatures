package sfiomn.legendarycreatures.entities.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import sfiomn.legendarycreatures.entities.ScorpionBabyEntity;
import sfiomn.legendarycreatures.entities.render.model.ScorpionBabyModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.annotation.Nullable;

public class ScorpionBabyRenderer extends GeoEntityRenderer<ScorpionBabyEntity> {

    private static final AABB INITIAL_AABB = new AABB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public ScorpionBabyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScorpionBabyModel());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));

        this.shadowRadius = 0.0f;
    }

    @Override
    public RenderType getRenderType(ScorpionBabyEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
