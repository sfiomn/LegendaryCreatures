package sfiomn.legendarycreatures.config.json;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class JsonSpawnInfo {
    @SerializedName("block_names")
    public Map<String, JsonChanceSpawn> breakingBlockNameSpawns;
    @SerializedName("block_tags")
    public Map<String, JsonChanceSpawn> breakingBlockTagSpawns;
    @SerializedName("entity_names")
    public Map<String, JsonChanceSpawn> killingEntityNameSpawns;
    @SerializedName("entity_type_tags")
    public Map<String, JsonChanceSpawn> killingEntityTypeTagSpawns;
    @SerializedName("black_list")
    public JsonBlackLists blackLists;

    public JsonSpawnInfo() {
        this.breakingBlockNameSpawns = new HashMap<>();
        this.breakingBlockTagSpawns = new HashMap<>();
        this.killingEntityNameSpawns = new HashMap<>();
        this.killingEntityTypeTagSpawns = new HashMap<>();
        this.blackLists = new JsonBlackLists();
    }

    public void clearAll() {
        this.breakingBlockNameSpawns.clear();
        this.breakingBlockTagSpawns.clear();
        this.killingEntityNameSpawns.clear();
        this.killingEntityTypeTagSpawns.clear();
        this.blackLists.clearAll();
    }
}
