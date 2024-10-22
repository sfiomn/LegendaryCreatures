package sfiomn.legendarycreatures.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModParticleProvider extends ParticleDescriptionProvider {

    public ModParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        List<ResourceLocation> corpseSplatterSprites = new ArrayList<>();
        for (int i=1; i<5; i++) {
            corpseSplatterSprites.add(new ResourceLocation(LegendaryCreatures.MOD_ID, "corpse_splatter_" + i));
        }
        spriteSet(ParticleTypeRegistry.CORPSE_SPLATTER.get(), corpseSplatterSprites);

        List<ResourceLocation> desertMojoSprites = new ArrayList<>();
        for (int i=1; i<6; i++) {
            desertMojoSprites.add(new ResourceLocation(LegendaryCreatures.MOD_ID, "desert_mojo_particles_" + i));
        }
        spriteSet(ParticleTypeRegistry.DESERT_MOJO_PARTICLE.get(), desertMojoSprites);

        List<ResourceLocation> wispParticleSprites = new ArrayList<>();
        wispParticleSprites.add(new ResourceLocation(LegendaryCreatures.MOD_ID, "wisp_particle"));
        spriteSet(ParticleTypeRegistry.WISP_PARTICLE.get(), wispParticleSprites);

        List<ResourceLocation> crowsParticleSprites = new ArrayList<>();
        crowsParticleSprites.add(new ResourceLocation(LegendaryCreatures.MOD_ID, "crows_particle"));
        spriteSet(ParticleTypeRegistry.CROWS_PARTICLE.get(), crowsParticleSprites);
    }
}
