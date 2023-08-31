package sfiomn.legendarycreatures.config.json;

import java.util.ArrayList;
import java.util.List;

public class JsonBlackLists {
    public List<String> biomeNames;
    public List<String> biomeCategories;
    public List<String> breakingBlockNames;
    public List<String> breakingBlockTags;
    public List<String> killingEntityNames;
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
