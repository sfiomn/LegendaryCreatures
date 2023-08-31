package sfiomn.legendarycreatures.config.json;

import java.util.HashMap;
import java.util.Map;

public class JsonSpawnInfo {
    public Map<String, JsonBiomeSpawn> biomeNameSpawns;
    public Map<String, JsonBiomeSpawn> biomeCategorySpawns;
    public Map<String, JsonChanceSpawn> breakingBlockNameSpawns;
    public Map<String, JsonChanceSpawn> breakingBlockTagSpawns;
    public Map<String, JsonChanceSpawn> killingEntityNameSpawns;
    public Map<String, JsonChanceSpawn> killingEntityTagSpawns;
    public JsonBlackLists blackLists;

    public JsonSpawnInfo() {
        this.biomeNameSpawns = new HashMap<>();
        this.biomeCategorySpawns = new HashMap<>();
        this.breakingBlockNameSpawns = new HashMap<>();
        this.breakingBlockTagSpawns = new HashMap<>();
        this.killingEntityNameSpawns = new HashMap<>();
        this.killingEntityTagSpawns = new HashMap<>();
        this.blackLists = new JsonBlackLists();
    }

    public void clearAll() {
        this.biomeNameSpawns.clear();
        this.biomeCategorySpawns.clear();
        this.breakingBlockNameSpawns.clear();
        this.breakingBlockTagSpawns.clear();
        this.killingEntityNameSpawns.clear();
        this.killingEntityTagSpawns.clear();
        this.blackLists.clearAll();
    }
}
