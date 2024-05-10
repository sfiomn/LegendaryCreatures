package sfiomn.legendarycreatures.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;

import java.util.Random;

public class ConvulsionEffect extends Effect
{
	public ConvulsionEffect()
	{
		super(EffectType.HARMFUL, 0x947b66);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier)
	{
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entity;
			Random rand = player.getRandom();

			if(!player.isCreative() || !player.isSpectator()) {
				if(rand.nextDouble() <= 0.1) {
					player.moveRelative((amplifier + 1), new Vector3d(rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D));
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
