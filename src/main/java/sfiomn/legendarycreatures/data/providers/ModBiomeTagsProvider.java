package sfiomn.legendarycreatures.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.ModBiomeTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LegendaryCreatures.MOD_ID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(ModBiomeTags.HAS_BULLFROG).add(Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
        this.tag(ModBiomeTags.HAS_DESERT_MOJO).add(Biomes.DESERT, Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA);
        this.tag(ModBiomeTags.HAS_FOREST_MOJO).add(Biomes.FOREST, Biomes.FLOWER_FOREST);
        this.tag(ModBiomeTags.HAS_HOUND).add(Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.DARK_FOREST, Biomes.WINDSWEPT_FOREST);
        this.tag(ModBiomeTags.HAS_SCARECROW).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
        this.tag(ModBiomeTags.HAS_SCORPION).add(Biomes.DESERT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
        this.tag(ModBiomeTags.HAS_WISP).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.TAIGA);
        this.tag(ModBiomeTags.HAS_NETHER_WIP).add(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST);
        this.tag(ModBiomeTags.HAS_ENDER_WISP).add(Biomes.THE_END, Biomes.SMALL_END_ISLANDS);
        this.tag(ModBiomeTags.HAS_PEACOCK_SPIDER).add(Biomes.BEACH, Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.FOREST, Biomes.FLOWER_FOREST);
        this.tag(ModBiomeTags.HAS_NETHER_WIP).add(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST);
    }
}
