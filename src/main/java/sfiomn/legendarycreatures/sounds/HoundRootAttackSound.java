package sfiomn.legendarycreatures.sounds;

import net.minecraft.client.Minecraft;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;
import sfiomn.legendarycreatures.registry.SoundRegistry;

import static sfiomn.legendarycreatures.entities.AnimatedCreatureEntity.ROOT_ATTACK;

public class HoundRootAttackSound {

    public static void startPlaying(AnimatedCreatureEntity houndEntity) {
        Minecraft.getInstance().getSoundManager().play(
                new StoppableSound(SoundRegistry.HOUND_ROOT_ATTACK.get(), houndEntity, (mob) -> mob.getAttackAnimation() != ROOT_ATTACK));
    }
}
