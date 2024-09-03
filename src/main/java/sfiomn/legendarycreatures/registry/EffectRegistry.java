package sfiomn.legendarycreatures.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.effects.ConvulsionEffect;
import sfiomn.legendarycreatures.effects.RootEffect;

public class EffectRegistry {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<MobEffect> ROOT = EFFECTS.register("root", RootEffect::new);
    public static final RegistryObject<MobEffect> CONVULSION = EFFECTS.register("convulsion", ConvulsionEffect::new);

    public static void register (IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
