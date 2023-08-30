package sfiomn.legendarycreatures.config.json;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.JsonTypeToken;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.util.Map;

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
		JsonConfig.registerBreakingBlockNameSpawn(MobEntityEnum.SCARECROW.mobId, "minecraft:wheat", 0.1);

		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.SCORPION.mobId, "minecraft:desert", 100, 1, 1);
		JsonConfig.registerBiomeNameSpawn(MobEntityEnum.SCORPION.mobId, "minecraft:desert_hills", 100, 1, 1);

		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.WISP.mobId, "plains", 20, 1, 1);
		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.WISP.mobId, "forest", 20, 1, 1);
		JsonConfig.registerBiomeCategorySpawn(MobEntityEnum.WISP.mobId, "taiga", 20, 1, 1);

		JsonConfig.registerKillingEntityNameSpawn(MobEntityEnum.CORPSE_EATER.mobId, "any", 0.01);
	}
	
	public static void clearContainers()
	{
		for (String mobId: JsonConfig.mobIdSpawnList.keySet()) {
			JsonConfig.mobIdSpawnList.get(mobId).clearAll();
		}
	}
	
	public static void processAllJson(File jsonDir)
	{
		for (String mobId: JsonConfig.mobIdSpawnList.keySet()) {
			String jsonFileName = mobId + "-spawn.json";
			JsonSpawnInfo jsonSpawns = processJson(jsonFileName, JsonConfig.mobIdSpawnList.get(mobId), jsonDir);

			if (jsonSpawns != null) {
				// Clear all defaults for this mobId if mobId-spawn.json file is present
				JsonConfig.mobIdSpawnList.get(mobId).clearAll();

				for (Map.Entry<String, JsonBiomeSpawn> entry : jsonSpawns.biomeNameSpawns.entrySet()) {
					JsonConfig.registerBiomeNameSpawn(mobId, entry.getKey(), entry.getValue().weight, entry.getValue().minGroup, entry.getValue().maxGroup);
				}

				for (Map.Entry<String, JsonBiomeSpawn> entry : jsonSpawns.biomeCategorySpawns.entrySet()) {
					JsonConfig.registerBiomeCategorySpawn(mobId, entry.getKey(), entry.getValue().weight, entry.getValue().minGroup, entry.getValue().maxGroup);
				}

				for (Map.Entry<String, JsonBreakingBlockSpawn> entry : jsonSpawns.breakingBlockNameSpawns.entrySet()) {
					JsonConfig.registerBreakingBlockNameSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonBreakingBlockSpawn> entry : jsonSpawns.breakingBlockTagSpawns.entrySet()) {
					JsonConfig.registerBreakingBlockTagSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonKillingEntitySpawn> entry : jsonSpawns.killingEntityNameSpawns.entrySet()) {
					JsonConfig.registerKillingEntityNameSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}
			}

			try {
				manuallyWriteToJson(jsonFileName, JsonConfig.mobIdSpawnList.get(mobId), jsonDir, false);
			} catch (Exception e) {
				LegendaryCreatures.LOGGER.error("Error writing merged JSON file", e);
			}
		}
	}
	
	@Nullable
	public static JsonSpawnInfo processJson(String jsonFileName, JsonSpawnInfo jsonSpawnInfo, File jsonDir)
	{
		try
		{
			return processUncaughtJson(jsonFileName, jsonSpawnInfo, jsonDir);
		}
		catch (Exception e)
		{
			LegendaryCreatures.LOGGER.error("Error managing JSON file: " + jsonFileName, e);

			return jsonSpawnInfo;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	public static JsonSpawnInfo processUncaughtJson(String jsonFileName, JsonSpawnInfo jsonSpawnInfo, File jsonDir) throws Exception
	{
		File jsonFile = new File(jsonDir, jsonFileName);

		Gson gson = buildNewGson();

		if (jsonFile.exists())
		{
			JsonObject jsonObject = gson.fromJson(new FileReader(jsonFile), JsonObject.class);
			if (jsonObject != null) {
				JsonSpawnInfo spawnInfo = new JsonSpawnInfo();

				JsonObject jsonBiomeNames = jsonObject.getAsJsonObject("biome_names");
				if (jsonBiomeNames != null) {
					for (Map.Entry<String, JsonElement> biomeName : jsonBiomeNames.entrySet()) {
						spawnInfo.biomeNameSpawns.put(biomeName.getKey(), new JsonBiomeSpawn(biomeName.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBiomeCategories = jsonObject.getAsJsonObject("biome_categories");
				if (jsonBiomeCategories != null){
					for (Map.Entry<String, JsonElement> biomeCategory : jsonBiomeCategories.entrySet()) {
						spawnInfo.biomeCategorySpawns.put(biomeCategory.getKey(), new JsonBiomeSpawn(biomeCategory.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBlockNames = jsonObject.getAsJsonObject("block_names");
				if (jsonBlockNames != null) {
					for (Map.Entry<String, JsonElement> blockName : jsonBlockNames.entrySet()) {
						spawnInfo.breakingBlockNameSpawns.put(blockName.getKey(), new JsonBreakingBlockSpawn(blockName.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonBlockTags = jsonObject.getAsJsonObject("block_tags");
				if (jsonBlockNames != null) {
					for (Map.Entry<String, JsonElement> blockTag : jsonBlockTags.entrySet()) {
						spawnInfo.breakingBlockTagSpawns.put(blockTag.getKey(), new JsonBreakingBlockSpawn(blockTag.getValue().getAsJsonObject()));
					}
				}

				JsonObject jsonEntityNames = jsonObject.getAsJsonObject("entity_names");
				if (jsonBlockNames != null) {
					for (Map.Entry<String, JsonElement> blockTag : jsonBlockTags.entrySet()) {
						spawnInfo.breakingBlockTagSpawns.put(blockTag.getKey(), new JsonBreakingBlockSpawn(blockTag.getValue().getAsJsonObject()));
					}
				}

				return spawnInfo;
			}
			return null;
		}
		else
		{
			JsonObject config = formattedConfig(gson, jsonSpawnInfo);

			FileUtils.write(jsonFile, gson.toJson(config, JsonObject.class), (String) null);
			
			return jsonSpawnInfo;
		}
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

		FileUtils.write(jsonFile, gson.toJson(config, JsonTypeToken.get()), (String) null);
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

		return config;
	}
	
	private static Gson buildNewGson()
	{
		return new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).excludeFieldsWithModifiers(Modifier.PRIVATE).create();
	}
}