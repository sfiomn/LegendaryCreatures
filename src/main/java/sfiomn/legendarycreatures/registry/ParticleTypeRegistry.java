package sfiomn.legendarycreatures.registry;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<BasicParticleType> WISP_PARTICLE = PARTICLE_TYPES.register("wisp_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> CORPSE_SPLATTER = PARTICLE_TYPES.register("corpse_splatter", () -> new BasicParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
