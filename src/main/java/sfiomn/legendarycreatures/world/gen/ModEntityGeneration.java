package sfiomn.legendarycreatures.world.gen;

import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.config.json.JsonBiomeSpawn;
import sfiomn.legendarycreatures.config.json.JsonConfig;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;

import java.util.List;
import java.util.Objects;

public class ModEntityGeneration {

    public static void onBiomeLoading(final BiomeLoadingEvent event) {

        ResourceLocation biomeName = event.getName();
        Biome.Category biomeCategory = event.getCategory();

        for (String mobId: JsonConfig.mobIdSpawnList.keySet()) {
            boolean canMobIdSpawn = false;
            JsonBiomeSpawn jsonBiomeSpawn = null;

            if (biomeName != null) {
                canMobIdSpawn = JsonConfig.mobIdSpawnList.get(mobId).biomeNameSpawns.containsKey(biomeName.toString());
                if (canMobIdSpawn)
                    jsonBiomeSpawn = JsonConfig.mobIdSpawnList.get(mobId).biomeNameSpawns.get(biomeName.toString());
            }

            if (!canMobIdSpawn) {
                canMobIdSpawn = JsonConfig.mobIdSpawnList.get(mobId).biomeCategorySpawns.containsKey(biomeCategory.getName());
                if (canMobIdSpawn)
                    jsonBiomeSpawn = JsonConfig.mobIdSpawnList.get(mobId).biomeCategorySpawns.get(biomeCategory.getName());
            }

            if (!canMobIdSpawn) {
                canMobIdSpawn = JsonConfig.mobIdSpawnList.get(mobId).biomeCategorySpawns.containsKey(biomeCategory.toString());
                if (canMobIdSpawn)
                    jsonBiomeSpawn = JsonConfig.mobIdSpawnList.get(mobId).biomeCategorySpawns.get(biomeCategory.toString());
            }

            if (canMobIdSpawn) {
                LegendaryCreatures.LOGGER.debug("Add " + mobId + " in biome " + biomeName + " with category " + biomeCategory.getName());
                addEntityToBiome(event.getSpawns(), mobId, jsonBiomeSpawn.weight, jsonBiomeSpawn.minGroup, jsonBiomeSpawn.maxGroup);
            }
        }
    }

    private static void addEntityToBiome(MobSpawnInfoBuilder spawns, String mobId,
                                         int weight, int minCount, int maxCount) {
        MobEntityEnum mobEntityEnum = MobEntityEnum.valueOfMobId(mobId);
        if (mobEntityEnum != null && weight > 0) {
            EntityType<?> entityType = (EntityType<?>) mobEntityEnum.entityRegistry.get();
            List<MobSpawnInfo.Spawners> base = spawns.getSpawner(entityType.getCategory());
            base.add(new MobSpawnInfo.Spawners(entityType, weight, minCount, maxCount));
        }
    }
}
