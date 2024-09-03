package sfiomn.legendarycreatures.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<SimpleParticleType> WISP_PARTICLE = PARTICLE_TYPES.register("wisp_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CORPSE_SPLATTER = PARTICLE_TYPES.register("corpse_splatter", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
