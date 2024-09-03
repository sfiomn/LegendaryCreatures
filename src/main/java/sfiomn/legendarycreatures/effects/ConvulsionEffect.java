package sfiomn.legendarycreatures.effects;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ConvulsionEffect extends MobEffect
{
	public ConvulsionEffect()
	{
		super(MobEffectCategory.HARMFUL, 0x947b66);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier)
	{
		if(entity instanceof Player player)
		{
			RandomSource rand = player.getRandom();

			if(!player.isCreative() || !player.isSpectator()) {
				if(rand.nextDouble() <= 0.1) {
					player.moveRelative((amplifier + 1), new Vec3(rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D));
				}
			}
		}
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier)
	{
		return true;
	}
}
