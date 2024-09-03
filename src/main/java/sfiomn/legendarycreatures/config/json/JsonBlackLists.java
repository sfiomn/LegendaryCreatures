package sfiomn.legendarycreatures.config.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JsonBlackLists {
    @SerializedName("block_names")
    public List<String> breakingBlockNames;
    @SerializedName("block_tags")
    public List<String> breakingBlockTags;
    @SerializedName("entity_names")
    public List<String> killingEntityNames;
    @SerializedName("entity_type_tags")
    public List<String> killingEntityTypeTags;

    public JsonBlackLists() {
        this.breakingBlockNames = new ArrayList<>();
        this.breakingBlockTags = new ArrayList<>();
        this.killingEntityNames = new ArrayList<>();
        this.killingEntityTypeTags = new ArrayList<>();
    }

    public void clearAll() {
        this.breakingBlockNames.clear();
        this.breakingBlockTags.clear();
        this.killingEntityTypeTags.clear();
    }
}
