package sfiomn.legendarycreatures.config;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlackLists {
    public List<String> breakingBlockNames;
    public List<TagKey<Block>> breakingBlockTags;
    public List<String> killingEntityNames;
    public List<TagKey<EntityType<?>>> killingEntityTypeTags;

    public BlackLists() {
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
