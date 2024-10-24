package sfiomn.legendarycreatures.integration;

import net.minecraftforge.fml.ModList;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.json.JsonConfig;


public final class IntegrationController
{
	public static void initIntegration()
	{
		ModList mods = ModList.get();
		
		if (mods.isLoaded("sereneseasons"))
			initSereneSeasons();
	}

	private static void initSereneSeasons()
	{
		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.SCARECROW.mobId, "sereneseasons:spring_crops", 0.01);
		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.SCARECROW.mobId, "sereneseasons:summer_crops", 0.01);
		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.SCARECROW.mobId, "sereneseasons:autumn_crops", 0.01);
		JsonConfig.registerBreakingBlockTagSpawn(MobEntityEnum.SCARECROW.mobId, "sereneseasons:winter_crops", 0.01);
	}
}
