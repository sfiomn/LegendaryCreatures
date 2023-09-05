package sfiomn.legendarycreatures.registry;

import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.effects.RootEffect;

public class EffectRegistry {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<Effect> ROOT = EFFECTS.register("root", RootEffect::new);


    public static void register (IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
