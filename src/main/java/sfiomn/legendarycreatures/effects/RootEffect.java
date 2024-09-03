package sfiomn.legendarycreatures.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RootEffect extends MobEffect
{
	public RootEffect()
	{
		super(MobEffectCategory.HARMFUL, 0x947b66);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier)
	{
		if(entity instanceof Player player)
		{
			Level level = entity.getCommandSenderWorld();

			player.makeStuckInBlock(level.getBlockState(player.blockPosition()), new Vec3(0.01, 0.01, 0.01));
		}
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier)
	{
		return true;
	}
}
