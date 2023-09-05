package sfiomn.legendarycreatures.config.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class JsonSpawnInfo {
    @SerializedName("biome_names")
    public Map<String, JsonBiomeSpawn> biomeNameSpawns;
    @SerializedName("biome_categories")
    public Map<String, JsonBiomeSpawn> biomeCategorySpawns;
    @SerializedName("block_names")
    public Map<String, JsonChanceSpawn> breakingBlockNameSpawns;
    @SerializedName("block_tags")
    public Map<String, JsonChanceSpawn> breakingBlockTagSpawns;
    @SerializedName("entity_names")
    public Map<String, JsonChanceSpawn> killingEntityNameSpawns;
    @SerializedName("entity_tags")
    public Map<String, JsonChanceSpawn> killingEntityTagSpawns;
    @SerializedName("black_list")
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
