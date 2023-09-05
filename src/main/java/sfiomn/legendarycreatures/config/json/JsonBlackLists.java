package sfiomn.legendarycreatures.config.json;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JsonBlackLists {
    @SerializedName("biome_names")
    public List<String> biomeNames;
    @SerializedName("biome_categories")
    public List<String> biomeCategories;
    @SerializedName("block_names")
    public List<String> breakingBlockNames;
    @SerializedName("block_tags")
    public List<String> breakingBlockTags;
    @SerializedName("entity_names")
    public List<String> killingEntityNames;
    @SerializedName("entity_tags")
    public List<String> killingEntityTags;

    public JsonBlackLists() {
        this.biomeNames = new ArrayList<>();
        this.biomeCategories = new ArrayList<>();
        this.breakingBlockNames = new ArrayList<>();
        this.breakingBlockTags = new ArrayList<>();
        this.killingEntityNames = new ArrayList<>();
        this.killingEntityTags = new ArrayList<>();
    }

    public void clearAll() {
        this.biomeNames.clear();
        this.biomeCategories.clear();
        this.breakingBlockNames.clear();
        this.breakingBlockTags.clear();
        this.killingEntityTags.clear();
    }
}
