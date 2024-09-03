package sfiomn.legendarycreatures.config.json;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.SpawnInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConfig
{
	public static Map<String, SpawnInfo> mobIdSpawnList = new HashMap<>();
	public static Map<String, JsonSpawnInfo> jsonMobIdSpawnList = new HashMap<>();

	public static void registerMobId(String mobId) {
		mobIdSpawnList.put(mobId, new SpawnInfo());
		jsonMobIdSpawnList.put(mobId, new JsonSpawnInfo());
	}

	public static void registerBreakingBlockNameSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		if (!mobIdSpawnList.get(mobId).breakingBlockNameSpawns.containsKey(registryName) && mobEntityEnum.canSpawnByBreaking()) {
			mobIdSpawnList.get(mobId).breakingBlockNameSpawns.put(registryName, new JsonChanceSpawn(chance));
			jsonMobIdSpawnList.get(mobId).breakingBlockNameSpawns.put(registryName, new JsonChanceSpawn(chance));
		}
	}

	public static void registerBreakingBlockTagSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		TagKey<Block> blockTag = TagKey.create(Registries.BLOCK, new ResourceLocation(registryName));

		if (!mobIdSpawnList.get(mobId).breakingBlockTagSpawns.containsKey(blockTag) && mobEntityEnum.canSpawnByBreaking()) {
			mobIdSpawnList.get(mobId).breakingBlockTagSpawns.put(blockTag, new JsonChanceSpawn(chance));
			jsonMobIdSpawnList.get(mobId).breakingBlockTagSpawns.put(registryName, new JsonChanceSpawn(chance));
		}
	}

	public static void registerKillingEntityNameSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		if (!mobIdSpawnList.get(mobId).killingEntityNameSpawns.containsKey(registryName) && mobEntityEnum.canSpawnByKilling()) {
			mobIdSpawnList.get(mobId).killingEntityNameSpawns.put(registryName, new JsonChanceSpawn(chance));
			jsonMobIdSpawnList.get(mobId).killingEntityNameSpawns.put(registryName, new JsonChanceSpawn(chance));
		}
	}

	public static void registerKillingEntityTagSpawn(String mobId, String registryName, double chance)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		TagKey<EntityType<?>> entityTag = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(registryName));

		if (!mobIdSpawnList.get(mobId).killingEntityTypeTagSpawns.containsKey(entityTag) && mobEntityEnum.canSpawnByKilling()) {
			mobIdSpawnList.get(mobId).killingEntityTypeTagSpawns.put(entityTag, new JsonChanceSpawn(chance));
			jsonMobIdSpawnList.get(mobId).killingEntityTypeTagSpawns.put(registryName, new JsonChanceSpawn(chance));
		}
	}

	public static void registerBreakingBlockNameBlackList(String mobId, List<String> registryNames)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		if (mobEntityEnum.canSpawnByBreaking()) {
			mobIdSpawnList.get(mobId).blackLists.breakingBlockNames.addAll(registryNames);
			jsonMobIdSpawnList.get(mobId).blackLists.breakingBlockNames.addAll(registryNames);
		}
	}

	public static void registerBreakingBlockTagBlackList(String mobId, List<String> registryNames)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		if (mobEntityEnum.canSpawnByBreaking()) {
			for (String registryName: registryNames) {
				mobIdSpawnList.get(mobId).blackLists.breakingBlockTags.add(TagKey.create(Registries.BLOCK, new ResourceLocation(registryName)));
			}
			jsonMobIdSpawnList.get(mobId).blackLists.breakingBlockTags.addAll(registryNames);
		}
	}

	public static void registerKillingEntityNameBlackList(String mobId, List<String> registryNames)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		if (mobEntityEnum.canSpawnByKilling()) {
			mobIdSpawnList.get(mobId).blackLists.killingEntityNames.addAll(registryNames);
			jsonMobIdSpawnList.get(mobId).blackLists.killingEntityNames.addAll(registryNames);
		}
	}

	public static void registerKillingEntityTagBlackList(String mobId, List<String> registryNames)
	{
		if (!isMobIdValid(mobId))
			return;
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		assert mobEntityEnum != null;

		if (mobEntityEnum.canSpawnByKilling()) {
			for (String registryName: registryNames) {
				mobIdSpawnList.get(mobId).blackLists.killingEntityTypeTags.add(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(registryName)));
			}
			jsonMobIdSpawnList.get(mobId).blackLists.killingEntityTypeTags.addAll(registryNames);
		}
	}

	private static boolean isMobIdValid(String mobId) {
		MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
		if (mobEntityEnum == null) {
			LegendaryCreatures.LOGGER.debug("Error - MobId " + mobId + " not present in MobEntityEnum.");
			return false;
		}
		if (!mobIdSpawnList.containsKey(mobId)) {
			LegendaryCreatures.LOGGER.debug("Error - MobId " + mobId + " not present in JsonConfig mobIdSpawnList, use registerMobId method in JsonRegistration.");
			return false;
		}
		return true;
	}
}
