package sfiomn.legendarycreatures.config;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import sfiomn.legendarycreatures.config.json.JsonBiomeSpawn;
import sfiomn.legendarycreatures.config.json.JsonChanceSpawn;

import java.util.HashMap;
import java.util.Map;

public class SpawnInfo {
    public Map<String, JsonChanceSpawn> breakingBlockNameSpawns;
    public Map<TagKey<Block>, JsonChanceSpawn> breakingBlockTagSpawns;
    public Map<String, JsonChanceSpawn> killingEntityNameSpawns;
    public Map<TagKey<EntityType<?>>, JsonChanceSpawn> killingEntityTypeTagSpawns;
    public BlackLists blackLists;

    public SpawnInfo() {
        this.breakingBlockNameSpawns = new HashMap<>();
        this.breakingBlockTagSpawns = new HashMap<>();
        this.killingEntityNameSpawns = new HashMap<>();
        this.killingEntityTypeTagSpawns = new HashMap<>();
        this.blackLists = new BlackLists();
    }

    public void clearAll() {
        this.breakingBlockNameSpawns.clear();
        this.breakingBlockTagSpawns.clear();
        this.killingEntityNameSpawns.clear();
        this.killingEntityTypeTagSpawns.clear();
        this.blackLists.clearAll();
    }
}
