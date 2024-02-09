package sfiomn.legendarycreatures.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class RenderTypeUtil {
    public static RenderType glowingNoTransparency(ResourceLocation textureLocation) {
        RenderType.State rendertype$state = RenderType.State.builder().setTextureState(new RenderState.TextureState(textureLocation, false, false))
                .setAlphaState(new RenderState.AlphaState(0.003921569F))
                .setCullState(new RenderState.CullState(false))
                .setDiffuseLightingState(new RenderState.DiffuseLightingState(true))
                .setFogState(new RenderState.FogState("black_fog",
                        () -> {
                            RenderSystem.fog(2918, 0.0F, 0.0F, 0.0F, 1.0F);
                            RenderSystem.enableFog();},
                        () -> {
                            FogRenderer.levelFogColor();
                            RenderSystem.disableFog();
                        }))
                .createCompositeState(false);
        if (!LegendaryCreatures.optifineLoaded)
            return RenderType.create("glowingNoTransparency", DefaultVertexFormats.NEW_ENTITY, 7, 256, false, true, rendertype$state);
        else
            return RenderType.eyes(textureLocation);
    }
}
