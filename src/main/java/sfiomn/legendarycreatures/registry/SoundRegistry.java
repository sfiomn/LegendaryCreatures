package sfiomn.legendarycreatures.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<SoundEvent> MOJO_HURT = registerSoundEvent("mojo_hurt");
    public static final RegistryObject<SoundEvent> MOJO_IDLE = registerSoundEvent("mojo_idle");
    public static final RegistryObject<SoundEvent> MOJO_STEP = registerSoundEvent("mojo_step");
    public static final RegistryObject<SoundEvent> MOJO_DEATH = registerSoundEvent("mojo_death");
    public static final RegistryObject<SoundEvent> MOJO_SPAWN = registerSoundEvent("mojo_spawn");
    public static final RegistryObject<SoundEvent> MOJO_BASE_ATTACK_HIT = registerSoundEvent("mojo_base_attack_hit");
    public static final RegistryObject<SoundEvent> GEONACH_IDLE = registerSoundEvent("geonach_idle");
    public static final RegistryObject<SoundEvent> GEONACH_HURT = registerSoundEvent("geonach_hurt");
    public static final RegistryObject<SoundEvent> GEONACH_DEATH = registerSoundEvent("geonach_death");
    public static final RegistryObject<SoundEvent> HOUND_DEATH = registerSoundEvent("hound_death");
    public static final RegistryObject<SoundEvent> HOUND_HURT = registerSoundEvent("hound_hurt");
    public static final RegistryObject<SoundEvent> HOUND_STEP = registerSoundEvent("hound_step");
    public static final RegistryObject<SoundEvent> HOUND_IDLE = registerSoundEvent("hound_idle");
    public static final RegistryObject<SoundEvent> HOUND_BASE_ATTACK_HIT = registerSoundEvent("hound_base_attack_hit");
    public static final RegistryObject<SoundEvent> HOUND_ROOT_ATTACK = registerSoundEvent("hound_root_attack");
    public static final RegistryObject<SoundEvent> SCARECROW_DEATH = registerSoundEvent("scarecrow_death");
    public static final RegistryObject<SoundEvent> SCARECROW_STEP = registerSoundEvent("scarecrow_step");
    public static final RegistryObject<SoundEvent> SCARECROW_SPAWN = registerSoundEvent("scarecrow_spawn");
    public static final RegistryObject<SoundEvent> SCARECROW_BASE_ATTACK_HIT = registerSoundEvent("scarecrow_base_attack_hit");
    public static final RegistryObject<SoundEvent> WISP_DEATH = registerSoundEvent("wisp_death");
    public static final RegistryObject<SoundEvent> WISP_IDLE = registerSoundEvent("wisp_idle");
    public static final RegistryObject<SoundEvent> SCORPION_DEATH = registerSoundEvent("scorpion_death");
    public static final RegistryObject<SoundEvent> SCORPION_HURT = registerSoundEvent("scorpion_hurt");
    public static final RegistryObject<SoundEvent> SCORPION_STEP = registerSoundEvent("scorpion_step");
    public static final RegistryObject<SoundEvent> SCORPION_IDLE = registerSoundEvent("scorpion_idle");
    public static final RegistryObject<SoundEvent> SCORPION_CLAWS_ATTACK_HIT = registerSoundEvent("scorpion_claws_hit");
    public static final RegistryObject<SoundEvent> SCORPION_TAIL_ATTACK_HIT = registerSoundEvent("scorpion_tail_hit");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_SPAWN = registerSoundEvent("corpse_eater_spawn");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_DEATH = registerSoundEvent("corpse_eater_death");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_HURT = registerSoundEvent("corpse_eater_hurt");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_STEP = registerSoundEvent("corpse_eater_step");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_IDLE = registerSoundEvent("corpse_eater_idle");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_ATTACK = registerSoundEvent("corpse_eater_attack");
    public static final RegistryObject<SoundEvent> CORPSE_EATER_ATTACK_HIT = registerSoundEvent("corpse_eater_attack_hit");
    public static final RegistryObject<SoundEvent> PEACOCK_SPIDER_DEATH = registerSoundEvent("peacock_spider_death");
    public static final RegistryObject<SoundEvent> PEACOCK_SPIDER_HURT = registerSoundEvent("peacock_spider_hurt");
    public static final RegistryObject<SoundEvent> PEACOCK_SPIDER_STEP = registerSoundEvent("peacock_spider_step");
    public static final RegistryObject<SoundEvent> PEACOCK_SPIDER_RUN = registerSoundEvent("peacock_spider_run");
    public static final RegistryObject<SoundEvent> PEACOCK_SPIDER_HISS = registerSoundEvent("peacock_spider_hiss");
    public static final RegistryObject<SoundEvent> BULLFROG_DEATH = registerSoundEvent("bullfrog_death");
    public static final RegistryObject<SoundEvent> BULLFROG_IDLE = registerSoundEvent("bullfrog_idle");
    public static final RegistryObject<SoundEvent> BULLFROG_STEP = registerSoundEvent("bullfrog_step");
    public static final RegistryObject<SoundEvent> BULLFROG_ATTACK = registerSoundEvent("bullfrog_attack");
    public static final RegistryObject<SoundEvent> BULLFROG_TONGUE_ATTACK = registerSoundEvent("bullfrog_tongue_attack");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                new ResourceLocation(LegendaryCreatures.MOD_ID, name)
            ));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
