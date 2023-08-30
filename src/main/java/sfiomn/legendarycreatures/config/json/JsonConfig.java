package sfiomn.legendarycreatures.config.json;

import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConfig
{
	public static Map<String, JsonSpawnInfo> mobIdSpawnList = new HashMap<>();

	public static void registerMobId(String mobId) {
		mobIdSpawnList.put(mobId, new JsonSpawnInfo());
	}

	public static void registerBiomeNameSpawn(String mobId, String registryName, int weight, int minGroup, int maxGroup)
	{
		if (!isMobIdValid(mobId)) {
			return;
		}
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;
		if (!mobIdSpawnList.get(mobId).biomeNameSpawns.containsKey(registryName) && mobEntityEnum.naturalSpawn) {
			mobIdSpawnList.get(mobId).biomeNameSpawns.put(registryName, new JsonBiomeSpawn(weight, minGroup, maxGroup));
		}
	}

	public static void registerBiomeCategorySpawn(String mobId, String registryName, int weight, int minGroup, int maxGroup)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;
		if (!mobIdSpawnList.get(mobId).biomeCategorySpawns.containsKey(registryName) && mobEntityEnum.naturalSpawn)
			mobIdSpawnList.get(mobId).biomeCategorySpawns.put(registryName, new JsonBiomeSpawn(weight, minGroup, maxGroup));
	}

	public static void registerBreakingBlockNameSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;
		if (!mobIdSpawnList.get(mobId).breakingBlockNameSpawns.containsKey(registryName) && mobEntityEnum.breakingBlockSpawn)
			mobIdSpawnList.get(mobId).breakingBlockNameSpawns.put(registryName, new JsonBreakingBlockSpawn(chance));
	}

	public static void registerBreakingBlockTagSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;
		if (!mobIdSpawnList.get(mobId).breakingBlockTagSpawns.containsKey(registryName) && mobEntityEnum.breakingBlockSpawn)
			mobIdSpawnList.get(mobId).breakingBlockTagSpawns.put(registryName, new JsonBreakingBlockSpawn(chance));
	}

	public static void registerKillingEntityNameSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;
		if (!mobIdSpawnList.get(mobId).killingEntityNameSpawns.containsKey(registryName) && mobEntityEnum.killingEntitySpawn)
			mobIdSpawnList.get(mobId).killingEntityNameSpawns.put(registryName, new JsonKillingEntitySpawn(chance));
	}

	private static boolean isMobIdValid(String mobId) {
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		if (mobEntityEnum == null) {
			LegendaryCreatures.LOGGER.debug("Error - MobId " + mobId + " not present in MobEntityEnum.");
			return false;
		}
		if (!mobIdSpawnList.containsKey(mobId)) {
			LegendaryCreatures.LOGGER.debug("Error - MobId " + mobId + " not present in JsonConfig, use registerMobId method in JsonRegistration.");
			return false;
		}
		return true;
	}
}
