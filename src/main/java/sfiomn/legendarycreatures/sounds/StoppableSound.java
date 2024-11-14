package sfiomn.legendarycreatures.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class StoppableSound extends AbstractTickableSoundInstance {
    private final AnimatedCreatureEntity mob;
    private final Function<AnimatedCreatureEntity, Boolean> stopCondition;

    public StoppableSound(SoundEvent soundEvent, AnimatedCreatureEntity mob, Function<AnimatedCreatureEntity, Boolean> stopCondition) {
        super(soundEvent, SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
        this.mob = mob;
        this.looping = true;
        this.volume = 1.0F;
        this.stopCondition = stopCondition;
    }

    @Override
    public void tick() {
        if (!this.mob.isAlive() || this.stopCondition.apply(this.mob))
            this.stop();

        else {
            this.x = this.mob.getX();
            this.y = this.mob.getY();
            this.z = this.mob.getZ();
        }
    }
}