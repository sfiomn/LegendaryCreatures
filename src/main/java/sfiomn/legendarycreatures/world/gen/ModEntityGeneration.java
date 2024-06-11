package sfiomn.legendarycreatures.world.gen;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.config.json.JsonBiomeSpawn;
import sfiomn.legendarycreatures.config.json.JsonBlackLists;
import sfiomn.legendarycreatures.config.json.JsonConfig;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModEntityGeneration {

    public static void onBiomeLoading(final BiomeLoadingEvent event) {

        ResourceLocation biomeName = event.getName();
        Biome.Category biomeCategory = event.getCategory();

        for (MobEntityEnum mobEntityEnum: MobEntityEnum.values()) {
            String mobId = mobEntityEnum.mobId;
            boolean canMobIdSpawn = false;
            JsonBiomeSpawn jsonBiomeSpawn = null;

            JsonBlackLists blackList = JsonConfig.mobIdSpawnList.get(mobId).blackLists;
            Map<String, JsonBiomeSpawn> biomeNameSpawns = JsonConfig.mobIdSpawnList.get(mobId).biomeNameSpawns;
            Map<String, JsonBiomeSpawn> biomeCategorySpawns = JsonConfig.mobIdSpawnList.get(mobId).biomeCategorySpawns;

            if ((biomeName != null && blackList.biomeNames.contains(biomeName.toString())) ||
                    blackList.biomeCategories.contains(biomeCategory.getName()) ||
                    blackList.biomeCategories.contains(biomeCategory.toString())) {
                LegendaryCreatures.LOGGER.debug("cancel spawn " + mobId + " black list biome name : " + biomeName + " / " + biomeCategory);
                continue;
            }

            if (biomeName != null) {
                canMobIdSpawn = biomeNameSpawns.containsKey(biomeName.toString());
                if (canMobIdSpawn)
                    jsonBiomeSpawn = biomeNameSpawns.get(biomeName.toString());
            }

            if (!canMobIdSpawn) {
                canMobIdSpawn = biomeCategorySpawns.containsKey(biomeCategory.getName());
                if (canMobIdSpawn)
                    jsonBiomeSpawn = biomeCategorySpawns.get(biomeCategory.getName());
            }

            if (!canMobIdSpawn) {
                canMobIdSpawn = biomeCategorySpawns.containsKey(biomeCategory.toString());
                if (canMobIdSpawn)
                    jsonBiomeSpawn = biomeCategorySpawns.get(biomeCategory.toString());
            }

            if (!canMobIdSpawn) {
                canMobIdSpawn = biomeNameSpawns.containsKey("default");
                if (canMobIdSpawn)
                    jsonBiomeSpawn = biomeNameSpawns.get("default");
            }

            if (!canMobIdSpawn) {
                canMobIdSpawn = biomeNameSpawns.containsKey("default:overworld");
                if (canMobIdSpawn && biomeCategory != Biome.Category.NETHER && biomeCategory != Biome.Category.THEEND)
                    jsonBiomeSpawn = biomeNameSpawns.get("default:overworld");
            }

            if (canMobIdSpawn) {
                LegendaryCreatures.LOGGER.debug("Add " + mobId + " [" + jsonBiomeSpawn.weight + ":" + jsonBiomeSpawn.minGroup + "-"+ jsonBiomeSpawn.maxGroup + "] in biome " + biomeName + " with category " + biomeCategory.getName());
                addEntityToBiome(event.getSpawns(), mobId, jsonBiomeSpawn.weight, jsonBiomeSpawn.minGroup, jsonBiomeSpawn.maxGroup);
            }
        }
    }

    private static void addEntityToBiome(MobSpawnInfoBuilder spawns, String mobId,
                                         int weight, int minCount, int maxCount) {
        MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
        if (mobEntityEnum != null && weight > 0) {
            EntityType<?> entityType = (EntityType<?>) mobEntityEnum.entityRegistry.get();
            spawns.addSpawn(entityType.getCategory(), new MobSpawnInfo.Spawners(entityType, weight, minCount, maxCount));
        }
    }
}
