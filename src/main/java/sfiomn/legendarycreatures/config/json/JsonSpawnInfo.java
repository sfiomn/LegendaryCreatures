package sfiomn.legendarycreatures.config.json;

import java.util.HashMap;
import java.util.Map;

public class JsonSpawnInfo {
    public Map<String, JsonBiomeSpawn> biomeNameSpawns;
    public Map<String, JsonBiomeSpawn> biomeCategorySpawns;
    public Map<String, JsonBreakingBlockSpawn> breakingBlockNameSpawns;
    public Map<String, JsonBreakingBlockSpawn> breakingBlockTagSpawns;
    public Map<String, JsonKillingEntitySpawn> killingEntityNameSpawns;

    public JsonSpawnInfo() {
        this.biomeNameSpawns = new HashMap<>();
        this.biomeCategorySpawns = new HashMap<>();
        this.breakingBlockNameSpawns = new HashMap<>();
        this.breakingBlockTagSpawns = new HashMap<>();
        this.killingEntityNameSpawns = new HashMap<>();
    }

    public void clearAll() {
        this.biomeNameSpawns.clear();
        this.biomeCategorySpawns.clear();
        this.breakingBlockNameSpawns.clear();
        this.breakingBlockTagSpawns.clear();
        this.killingEntityNameSpawns.clear();
    }
}
