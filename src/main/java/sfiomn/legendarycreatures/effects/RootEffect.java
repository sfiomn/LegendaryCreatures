package sfiomn.legendarycreatures.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RootEffect extends Effect
{
	public RootEffect()
	{
		super(EffectType.HARMFUL, 0x947b66);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier)
	{
		if(entity instanceof PlayerEntity)
		{
			World world = entity.getCommandSenderWorld();
			PlayerEntity player = (PlayerEntity) entity;

			player.makeStuckInBlock(world.getBlockState(player.blockPosition()), new Vector3d(0.01, 0.01, 0.01));
		}
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier)
	{
		return true;
	}
}
