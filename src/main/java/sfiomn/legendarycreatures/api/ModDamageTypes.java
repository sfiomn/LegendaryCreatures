package sfiomn.legendarycreatures.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import sfiomn.legendarycreatures.LegendaryCreatures;

import static net.minecraft.world.damagesource.DamageEffects.*;

public class ModDamageTypes
{
	public static final ResourceKey<DamageType> ROOT_ATTACK = registerKey("root_attack");


	public static ResourceKey<DamageType> registerKey(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LegendaryCreatures.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<DamageType> context) {
		context.register(ROOT_ATTACK, new DamageType(LegendaryCreatures.MOD_ID + ".root_attack", DamageScaling.NEVER,0.1f, HURT));
	}
}
