package sfiomn.legendarycreatures.config.json;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.JsonTypeToken;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class JsonConfigRegistration
{
	
	public static void init(File configDir)
	{
		registerMobIds();

		registerDefaults();
		
		processAllJson(configDir);
	}

	public static void registerMobIds() {
		for (MobEntityEnum mobEntityEnum: MobEntityEnum.values()) {
			JsonConfig.registerMobId(mobEntityEnum.mobId);
		}
	}
	
	public static void registerDefaults()
	{
		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.DESERT_MOJO.mobId, "minecraft:cactus", 0.1);

		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.DESERT_MOJO.mobId, "minecraft:desert", 100, 1, 1);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.DESERT_MOJO.mobId, "minecraft:desert_hills", 100, 1, 1);

		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.FOREST_MOJO.mobId, "minecraft:flowers", 0.1);

		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:forest", 100, 1, 2);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:birch_forest", 100, 1, 2);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:birch_forest_hills", 100, 1, 2);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:dark_forest", 100, 1, 2);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:dark_forest_hills", 100, 1, 2);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:flower_forest", 100, 1, 2);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.HOUND.mobId, "minecraft:wooded_hills", 100, 1, 2);

		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.SCARECROW.mobId, "plains", 100, 1, 1);
		JsonConfig.registerBiomeNameBlackList(MobEntityEnum.SCARECROW.mobId, Collections.singletonList("minecraft:snowy_tundra"));
		JsonConfig.registerBreakingBlockNameSpawn(MobEntityEnum.SCARECROW.mobId, "minecraft:wheat", 0.1);
		JsonConfig.registerKillingEntityNameSpawn(MobEntityEnum.SCARECROW.mobId, "minecraft:chicken", 0.3);

		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.SCORPION.mobId, "minecraft:desert", 100, 1, 1);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.SCORPION.mobId, "minecraft:desert_hills", 100, 1, 1);

		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.WISP.mobId, "plains", 20, 1, 1);
		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.WISP.mobId, "forest", 20, 1, 1);
		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.WISP.mobId, "taiga", 20, 1, 1);

		JsonConfig.registerKillingEntityNameSpawn(MobEntityEnum.CORPSE_EATER.mobId, "default", 0.1);
		JsonConfig.registerKillingEntityTagSpawn(MobEntityEnum.CORPSE_EATER.mobId, "minecraft:raiders", 0.4);
		JsonConfig.registerKillingEntityNameBlackList(MobEntityEnum.CORPSE_EATER.mobId, Arrays.asList("minecraft:bee", "minecraft:cow", "minecraft:pillager", LegendaryCreatures.MOD_ID +":"+ MobEntityEnum.CORPSE_EATER.mobId));
	}
	
	public static void clearContainers()
	{
		for (String mobId: JsonConfig.mobIdSpawnList.keySet()) {
			JsonConfig.mobIdSpawnList.get(mobId).clearAll();
		}
	}
	
	public static void processAllJson(File jsonDir)
	{
		for (MobEntityEnum mobEntityEnum: MobEntityEnum.values()) {
			String mobId = mobEntityEnum.mobId;
			String jsonFileName = mobId + "-spawn.json";
			JsonSpawnInfo jsonSpawns = processJson(jsonFileName, jsonDir);

			if (jsonSpawns != null) {
				// Clear all defaults for this mobId if mobId-spawn.json file is present
				JsonConfig.mobIdSpawnList.get(mobId).clearAll();

				for (Map.Entry<String, JsonBiomeSpawn> entry : jsonSpawns.biomeNameSpawns.entrySet()) {
					JsonConfig.registerBiomeNameSpawn(mobId, entry.getKey(), entry.getValue().weight, entry.getValue().minGroup, entry.getValue().maxGroup);
				}

				for (Map.Entry<String, JsonBiomeSpawn> entry : jsonSpawns.biomeCategorySpawns.entrySet()) {
					JsonConfig.registerBiomeCategorySpawn(mobId, entry.getKey(), entry.getValue().weight, entry.getValue().minGroup, entry.getValue().maxGroup);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.breakingBlockNameSpawns.entrySet()) {
					JsonConfig.registerBreakingBlockNameSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.breakingBlockTagSpawns.entrySet()) {
					JsonConfig.registerBreakingBlockTagSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.killingEntityNameSpawns.entrySet()) {
					JsonConfig.registerKillingEntityNameSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.killingEntityTagSpawns.entrySet()) {
					JsonConfig.registerKillingEntityTagSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				JsonConfig.registerBiomeNameBlackList(mobId, jsonSpawns.blackLists.biomeNames);
				JsonConfig.registerBiomeCategoryBlackList(mobId, jsonSpawns.blackLists.biomeCategories);
				JsonConfig.registerBreakingBlockNameBlackList(mobId, jsonSpawns.blackLists.breakingBlockNames);
				JsonConfig.registerBreakingBlockTagBlackList(mobId, jsonSpawns.blackLists.breakingBlockTags);
				JsonConfig.registerKillingEntityNameBlackList(mobId, jsonSpawns.blackLists.killingEntityNames);
				JsonConfig.registerKillingEntityTagBlackList(mobId, jsonSpawns.blackLists.killingEntityTags);
			}

			try {
				manuallyWriteToJson(jsonFileName, JsonConfig.mobIdSpawnList.get(mobId), jsonDir, false);
			} catch (Exception e) {
				LegendaryCreatures.LOGGER.error("Error writing JSON file", e);
			}
		}
	}
	
	@Nullable
	public static JsonSpawnInfo processJson(String jsonFileName, File jsonDir)
	{
		try
		{
			return processUncaughtJson(jsonFileName, jsonDir);
		}
		catch (Exception e)
		{
			LegendaryCreatures.LOGGER.error("Error managing JSON file: " + jsonFileName, e);

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	public static JsonSpawnInfo processUncaughtJson(String jsonFileName, File jsonDir) throws Exception
	{
		File jsonFile = new File(jsonDir, jsonFileName);

		Gson gson = buildNewGson();

		if (jsonFile.exists())
		{
			JsonObject jsonSpawnInfoConfigFile = gson.fromJson(new FileReader(jsonFile), JsonObject.class);
			if (jsonSpawnInfoConfigFile != null) {
				JsonSpawnInfo spawnInfo = new JsonSpawnInfo();

				JsonObject jsonBiomeNames = jsonSpawnInfoConfigFile.getAsJsonObject("biome_names");
				if (jsonBiomeNames != null) {
					for (Map.Entry<String, JsonElement> biomeName : jsonBiomeNames.entrySet()) {
						spawnInfo.biomeNameSpawns.put(biomeName.getKey(), new JsonBiomeSpawn(biomeName.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBiomeCategories = jsonSpawnInfoConfigFile.getAsJsonObject("biome_categories");
				if (jsonBiomeCategories != null){
					for (Map.Entry<String, JsonElement> biomeCategory : jsonBiomeCategories.entrySet()) {
						spawnInfo.biomeCategorySpawns.put(biomeCategory.getKey(), new JsonBiomeSpawn(biomeCategory.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBlockNames = jsonSpawnInfoConfigFile.getAsJsonObject("block_names");
				if (jsonBlockNames != null) {
					for (Map.Entry<String, JsonElement> blockName : jsonBlockNames.entrySet()) {
						spawnInfo.breakingBlockNameSpawns.put(blockName.getKey(), new JsonChanceSpawn(blockName.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBlockTags = jsonSpawnInfoConfigFile.getAsJsonObject("block_tags");
				if (jsonBlockTags != null) {
					for (Map.Entry<String, JsonElement> blockTag : jsonBlockTags.entrySet()) {
						spawnInfo.breakingBlockTagSpawns.put(blockTag.getKey(), new JsonChanceSpawn(blockTag.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonEntityNames = jsonSpawnInfoConfigFile.getAsJsonObject("entity_names");
				if (jsonEntityNames != null) {
					for (Map.Entry<String, JsonElement> entityName : jsonEntityNames.entrySet()) {
						spawnInfo.killingEntityNameSpawns.put(entityName.getKey(), new JsonChanceSpawn(entityName.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonEntityTags = jsonSpawnInfoConfigFile.getAsJsonObject("entity_tags");
				if (jsonEntityTags != null) {
					for (Map.Entry<String, JsonElement> entityTag : jsonEntityTags.entrySet()) {
						spawnInfo.killingEntityTagSpawns.put(entityTag.getKey(), new JsonChanceSpawn(entityTag.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBlackLists = jsonSpawnInfoConfigFile.getAsJsonObject("black_list");
				if (jsonBlackLists != null) {
					JsonArray jsonBiomeNamesBlackList = jsonBlackLists.getAsJsonArray("biome_names");
					if (jsonBiomeNamesBlackList != null) {
						for (int i=0; i<jsonBiomeNamesBlackList.size(); i++)
							spawnInfo.blackLists.biomeNames.add(jsonBiomeNamesBlackList.get(i).getAsString());
					}
					JsonArray jsonBiomeCategoriesBlackList = jsonBlackLists.getAsJsonArray("biome_categories");
					if (jsonBiomeCategoriesBlackList != null) {
						for (int i=0; i<jsonBiomeCategoriesBlackList.size(); i++)
							spawnInfo.blackLists.biomeCategories.add(jsonBiomeCategoriesBlackList.get(i).getAsString());
					}
					JsonArray jsonBlockNamesBlackList = jsonBlackLists.getAsJsonArray("block_names");
					if (jsonBlockNamesBlackList != null) {
						for (int i=0; i<jsonBlockNamesBlackList.size(); i++)
							spawnInfo.blackLists.breakingBlockNames.add(jsonBlockNamesBlackList.get(i).getAsString());
					}
					JsonArray jsonBlockTagsBlackList = jsonBlackLists.getAsJsonArray("block_tags");
					if (jsonBlockTagsBlackList != null) {
						for (int i=0; i<jsonBlockTagsBlackList.size(); i++)
							spawnInfo.blackLists.breakingBlockTags.add(jsonBlockTagsBlackList.get(i).getAsString());
					}
					JsonArray jsonEntityNamesBlackList = jsonBlackLists.getAsJsonArray("entity_names");
					if (jsonEntityNamesBlackList != null) {
						for (int i=0; i<jsonEntityNamesBlackList.size(); i++)
							spawnInfo.blackLists.killingEntityNames.add(jsonEntityNamesBlackList.get(i).getAsString());
					}
					JsonArray jsonEntityTagsBlackList = jsonBlackLists.getAsJsonArray("entity_tags");
					if (jsonEntityTagsBlackList != null) {
						for (int i=0; i<jsonEntityTagsBlackList.size(); i++)
							spawnInfo.blackLists.killingEntityTags.add(jsonEntityTagsBlackList.get(i).getAsString());
					}
				}

				return spawnInfo;
			}
			return null;
		}
		return null;
	}
	
	private static void manuallyWriteToJson(String jsonFileName, JsonSpawnInfo jsonSpawnInfo, File jsonDir, boolean forceWrite) throws Exception
	{
		File jsonFile = new File(jsonDir, jsonFileName);
		if (jsonFile.exists())
		{
			LegendaryCreatures.LOGGER.debug(jsonFile.getName() + " already exists!");
			
			if (forceWrite)
				LegendaryCreatures.LOGGER.debug("Overriding...");
			else
				return;
		}
		Gson gson = buildNewGson();
		JsonObject config = formattedConfig(gson, jsonSpawnInfo);

		FileUtils.write(jsonFile, gson.toJson(config, JsonObject.class), (String) null);
	}

	private static JsonObject formattedConfig(Gson gson, JsonSpawnInfo jsonSpawnInfo) {
		JsonObject config = new JsonObject();

		JsonElement biomeNameConfig = gson.toJsonTree(jsonSpawnInfo.biomeNameSpawns);
		config.add("biome_names", biomeNameConfig);

		JsonElement biomeCategoryConfig = gson.toJsonTree(jsonSpawnInfo.biomeCategorySpawns);
		config.add("biome_categories", biomeCategoryConfig);

		JsonElement blockNameConfig = gson.toJsonTree(jsonSpawnInfo.breakingBlockNameSpawns);
		config.add("block_names", blockNameConfig);

		JsonElement blockTagConfig = gson.toJsonTree(jsonSpawnInfo.breakingBlockTagSpawns);
		config.add("block_tags", blockTagConfig);

		JsonElement entityNameConfig = gson.toJsonTree(jsonSpawnInfo.killingEntityNameSpawns);
		config.add("entity_names", entityNameConfig);

		JsonElement entityTagConfig = gson.toJsonTree(jsonSpawnInfo.killingEntityTagSpawns);
		config.add("entity_tags", entityTagConfig);

		JsonObject blackListConfig = new JsonObject();

		JsonElement biomeNameBlackList = gson.toJsonTree(jsonSpawnInfo.blackLists.biomeNames);
		blackListConfig.add("biome_names", biomeNameBlackList);

		JsonElement biomeCategoryBlackList = gson.toJsonTree(jsonSpawnInfo.blackLists.biomeCategories);
		blackListConfig.add("biome_categories", biomeCategoryBlackList);

		JsonElement blockNameBlackList = gson.toJsonTree(jsonSpawnInfo.blackLists.breakingBlockNames);
		blackListConfig.add("block_names", blockNameBlackList);

		JsonElement blockTagBlackList = gson.toJsonTree(jsonSpawnInfo.blackLists.breakingBlockTags);
		blackListConfig.add("block_tags", blockTagBlackList);

		JsonElement entityNameBlackList = gson.toJsonTree(jsonSpawnInfo.blackLists.killingEntityNames);
		blackListConfig.add("entity_names", entityNameBlackList);

		JsonElement entityTagBlackList = gson.toJsonTree(jsonSpawnInfo.blackLists.killingEntityTags);
		blackListConfig.add("entity_tags", entityTagBlackList);

		config.add("black_list", blackListConfig);

		return config;
	}
	
	private static Gson buildNewGson()
	{
		return new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).excludeFieldsWithModifiers(Modifier.PRIVATE).create();
	}
}