package sfiomn.legendarycreatures.api;

import net.minecraft.util.DamageSource;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class DamageSources
{
	public static final DamageSource ROOT_ATTACK = new DamageSource(LegendaryCreatures.MOD_ID + ".root_attack").bypassArmor();
}
