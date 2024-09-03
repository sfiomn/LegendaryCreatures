package sfiomn.legendarycreatures.config.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.util.Arrays;
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

		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.FOREST_MOJO.mobId, "minecraft:flowers", 0.1);

		JsonConfig.registerBreakingBlockNameSpawn(MobEntityEnum.SCARECROW.mobId, "minecraft:wheat", 0.1);
		JsonConfig.registerKillingEntityNameSpawn(MobEntityEnum.SCARECROW.mobId, "minecraft:chicken", 0.3);

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

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.breakingBlockNameSpawns.entrySet()) {
					JsonConfig.registerBreakingBlockNameSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.breakingBlockTagSpawns.entrySet()) {
					JsonConfig.registerBreakingBlockTagSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.killingEntityNameSpawns.entrySet()) {
					JsonConfig.registerKillingEntityNameSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				for (Map.Entry<String, JsonChanceSpawn> entry : jsonSpawns.killingEntityTypeTagSpawns.entrySet()) {
					JsonConfig.registerKillingEntityTagSpawn(mobId, entry.getKey(), entry.getValue().chance);
				}

				JsonConfig.registerBreakingBlockNameBlackList(mobId, jsonSpawns.blackLists.breakingBlockNames);
				JsonConfig.registerBreakingBlockTagBlackList(mobId, jsonSpawns.blackLists.breakingBlockTags);
				JsonConfig.registerKillingEntityNameBlackList(mobId, jsonSpawns.blackLists.killingEntityNames);
				JsonConfig.registerKillingEntityTagBlackList(mobId, jsonSpawns.blackLists.killingEntityTypeTags);
			}

			try {
				manuallyWriteToJson(jsonFileName, JsonConfig.jsonMobIdSpawnList.get(mobId), jsonDir, false);
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

		if (jsonFile.exists())
		{
			Gson gson = buildNewGson();
			return gson.fromJson(new FileReader(jsonFile), new TypeToken<JsonSpawnInfo>(){}.getType());
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

		FileUtils.write(jsonFile, gson.toJson(jsonSpawnInfo, JsonSpawnInfo.class), (String) null);
	}
	
	private static Gson buildNewGson()
	{
		return new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).excludeFieldsWithModifiers(Modifier.PRIVATE).create();
	}
}