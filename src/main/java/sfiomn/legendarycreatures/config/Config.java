package sfiomn.legendarycreatures.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import sfiomn.legendarycreatures.LegendaryCreatures;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Config
{
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	
	static
	{
		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}
	
	public static void register()
	{
		try
		{
			Files.createDirectory(LegendaryCreatures.modConfigPath);
			Files.createDirectory(LegendaryCreatures.modConfigJson);
		}
		catch (FileAlreadyExistsException ignored) {}
		catch (IOException e)
		{
			LegendaryCreatures.LOGGER.error("Failed to create Legendary Creatures config directories");
			e.printStackTrace();
		}

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC, LegendaryCreatures.MOD_ID + "/" + LegendaryCreatures.MOD_ID + "-common.toml");
	}
	
	public static class Common
	{
		public final ForgeConfigSpec.BooleanValue desertMojoBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue desertMojoKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue forestMojoBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue forestMojoKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue houndBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue houndKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue scarecrowBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue scarecrowKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue scorpionBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue scorpionKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue scorpionBabyBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue scorpionBabyKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue wispBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue wispKillingEntitySpawn;
		public final ForgeConfigSpec.ConfigValue<List<Integer>> wispPurseXpReward;
		public final ForgeConfigSpec.BooleanValue netherWispBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue netherWispKillingEntitySpawn;
		public final ForgeConfigSpec.ConfigValue<List<Integer>> netherWispPurseXpReward;
		public final ForgeConfigSpec.BooleanValue enderWispBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue enderWispKillingEntitySpawn;
		public final ForgeConfigSpec.ConfigValue<List<Integer>> enderWispPurseXpReward;
		public final ForgeConfigSpec.BooleanValue corpseEaterBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue corpseEaterKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue peacockSpiderBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue peacockSpiderKillingEntitySpawn;
		public final ForgeConfigSpec.BooleanValue bullfrogBreakingBlockSpawn;
		public final ForgeConfigSpec.BooleanValue bullfrogKillingEntitySpawn;

		Common(ForgeConfigSpec.Builder builder)
		{
			builder.comment(new String [] {
						" Options related to mob spawning",
						" Whether or not the mob will spawn when breaking blocks defined in json/[mobName]-spawn.json",
						" Whether or not the mob will spawn when killing entities defined in json/[mobName]-spawn.json"
					}).push("MobSpawning");

			builder.push("desert_mojo");
			desertMojoBreakingBlockSpawn = builder.define("Desert Mojo Breaking Block Spawn ", true);
			desertMojoKillingEntitySpawn = builder.define("Desert Mojo Killing Entity Spawn ", true);
			builder.pop();

			builder.push("forest_mojo");
			forestMojoBreakingBlockSpawn = builder.define("Forest Mojo Breaking Block Spawn ", true);
			forestMojoKillingEntitySpawn = builder.define("Forest Mojo Killing Entity Spawn ", true);
			builder.pop();

			builder.push("hound");
			houndBreakingBlockSpawn = builder.define("Hound Breaking Block Spawn ", true);
			houndKillingEntitySpawn = builder.define("Hound Killing Entity Spawn ", true);
			builder.pop();

			builder.push("Scarecrow");
			scarecrowBreakingBlockSpawn = builder.define("Scarecrow Breaking Block Spawn ", true);
			scarecrowKillingEntitySpawn = builder.define("Scarecrow Killing Entity Spawn ", true);
			builder.pop();

			builder.push("scorpion");
			scorpionBreakingBlockSpawn = builder.define("Scorpion Breaking Block Spawn ", true);
			scorpionKillingEntitySpawn = builder.define("Scorpion Killing Entity Spawn ", true);
			builder.pop();

			builder.push("scorpion_baby");
			scorpionBabyBreakingBlockSpawn = builder.define("Baby Scorpion Breaking Block Spawn ", true);
			scorpionBabyKillingEntitySpawn = builder.define("Baby Scorpion Killing Entity Spawn ", true);
			builder.pop();

			builder.push("wisp");
			wispBreakingBlockSpawn = builder.define("Wisp Breaking Block Spawn ", true);
			wispKillingEntitySpawn = builder.define("Wisp Killing Entity Spawn ", true);
			wispPurseXpReward = builder.define("Wisp Purse Xp Reward Range ", Arrays.asList(30, 30));
			builder.pop();

			builder.push("nether_wisp");
			netherWispBreakingBlockSpawn = builder.define("Nether Wisp Breaking Block Spawn ", true);
			netherWispKillingEntitySpawn = builder.define("Nether Wisp Killing Entity Spawn ", true);
			netherWispPurseXpReward = builder.define("Nether Wisp Purse Xp Reward Range ", Arrays.asList(80, 80));
			builder.pop();

			builder.push("ender_wisp");
			enderWispBreakingBlockSpawn = builder.define("Ender Wisp Breaking Block Spawn ", true);
			enderWispKillingEntitySpawn = builder.define("Ender Wisp Killing Entity Spawn ", true);
			enderWispPurseXpReward = builder.define("Ender Wisp Purse Xp Reward Range ", Arrays.asList(240, 240));
			builder.pop();

			builder.push("corpse_eater");
			corpseEaterBreakingBlockSpawn = builder.define("Corpse Eater Breaking Block Spawn ", true);
			corpseEaterKillingEntitySpawn = builder.define("Corpse Eater Killing Entity Spawn ", true);
			builder.pop();

			builder.push("peacock_spider");
			peacockSpiderBreakingBlockSpawn = builder.define("Peacock Spider Breaking Block Spawn ", true);
			peacockSpiderKillingEntitySpawn = builder.define("Peacock Spider Killing Entity Spawn ", true);
			builder.pop();

			builder.push("bullfrog");
			bullfrogBreakingBlockSpawn = builder.define("Bullfrog Breaking Block Spawn ", true);
			bullfrogKillingEntitySpawn = builder.define("Bullfrog Killing Entity Spawn ", true);
			builder.pop();

			builder.pop();
		}
	}
	
	public static class Baked
	{
		public static boolean desertMojoBreakingBlockSpawn;
		public static boolean desertMojoKillingEntitySpawn;
		public static boolean forestMojoBreakingBlockSpawn;
		public static boolean forestMojoKillingEntitySpawn;
		public static boolean houndBreakingBlockSpawn;
		public static boolean houndKillingEntitySpawn;
		public static boolean scarecrowBreakingBlockSpawn;
		public static boolean scarecrowKillingEntitySpawn;
		public static boolean scorpionBreakingBlockSpawn;
		public static boolean scorpionKillingEntitySpawn;
		public static boolean scorpionBabyBreakingBlockSpawn;
		public static boolean scorpionBabyKillingEntitySpawn;
		public static boolean wispBreakingBlockSpawn;
		public static boolean wispKillingEntitySpawn;
		public static List<Integer> wispPurseXpReward;
		public static boolean netherWispBreakingBlockSpawn;
		public static boolean netherWispKillingEntitySpawn;
		public static List<Integer> netherWispPurseXpReward;
		public static boolean enderWispBreakingBlockSpawn;
		public static boolean enderWispKillingEntitySpawn;
		public static List<Integer> enderWispPurseXpReward;
		public static boolean corpseEaterBreakingBlockSpawn;
		public static boolean corpseEaterKillingEntitySpawn;
		public static boolean peacockSpiderBreakingBlockSpawn;
		public static boolean peacockSpiderKillingEntitySpawn;
		public static boolean bullfrogBreakingBlockSpawn;
		public static boolean bullfrogKillingEntitySpawn;

		public static void bakeCommon()
		{
			try
			{
				desertMojoBreakingBlockSpawn = COMMON.desertMojoBreakingBlockSpawn.get();
				desertMojoKillingEntitySpawn = COMMON.desertMojoKillingEntitySpawn.get();
				forestMojoBreakingBlockSpawn = COMMON.forestMojoBreakingBlockSpawn.get();
				forestMojoKillingEntitySpawn = COMMON.forestMojoKillingEntitySpawn.get();
				houndBreakingBlockSpawn = COMMON.houndBreakingBlockSpawn.get();
				houndKillingEntitySpawn = COMMON.houndKillingEntitySpawn.get();
				scarecrowBreakingBlockSpawn = COMMON.scarecrowBreakingBlockSpawn.get();
				scarecrowKillingEntitySpawn = COMMON.scarecrowKillingEntitySpawn.get();
				scorpionBreakingBlockSpawn = COMMON.scorpionBreakingBlockSpawn.get();
				scorpionKillingEntitySpawn = COMMON.scorpionKillingEntitySpawn.get();
				scorpionBabyBreakingBlockSpawn = COMMON.scorpionBabyBreakingBlockSpawn.get();
				scorpionBabyKillingEntitySpawn = COMMON.scorpionBabyKillingEntitySpawn.get();
				wispBreakingBlockSpawn = COMMON.wispBreakingBlockSpawn.get();
				wispKillingEntitySpawn = COMMON.wispKillingEntitySpawn.get();
				wispPurseXpReward = COMMON.wispPurseXpReward.get();
				netherWispBreakingBlockSpawn = COMMON.netherWispBreakingBlockSpawn.get();
				netherWispKillingEntitySpawn = COMMON.netherWispKillingEntitySpawn.get();
				netherWispPurseXpReward = COMMON.netherWispPurseXpReward.get();
				enderWispBreakingBlockSpawn = COMMON.enderWispBreakingBlockSpawn.get();
				enderWispKillingEntitySpawn = COMMON.enderWispKillingEntitySpawn.get();
				enderWispPurseXpReward = COMMON.enderWispPurseXpReward.get();
				corpseEaterBreakingBlockSpawn = COMMON.corpseEaterBreakingBlockSpawn.get();
				corpseEaterKillingEntitySpawn = COMMON.corpseEaterKillingEntitySpawn.get();
				peacockSpiderBreakingBlockSpawn = COMMON.peacockSpiderBreakingBlockSpawn.get();
				peacockSpiderKillingEntitySpawn = COMMON.peacockSpiderKillingEntitySpawn.get();
				bullfrogBreakingBlockSpawn = COMMON.bullfrogBreakingBlockSpawn.get();
				bullfrogKillingEntitySpawn = COMMON.bullfrogKillingEntitySpawn.get();
			}
			catch (Exception e)
			{
				LegendaryCreatures.LOGGER.warn("An exception was caused trying to load the common config for Survival Overhaul");
				e.printStackTrace();
			}
		}
	}
}
