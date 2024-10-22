package sfiomn.legendarycreatures.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
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
        this.tag(ModBiomeTags.HAS_BULLFROG).addTag(Tags.Biomes.IS_SWAMP);
        this.tag(ModBiomeTags.HAS_HOUND).addTag(BiomeTags.IS_FOREST);
        this.tag(ModBiomeTags.HAS_SCORPION).addTag(Tags.Biomes.IS_DESERT);
        this.tag(ModBiomeTags.HAS_WISP).addTag(Tags.Biomes.IS_PLAINS).addTag(BiomeTags.IS_FOREST);
        this.tag(ModBiomeTags.HAS_NETHER_WIP).addTag(BiomeTags.IS_NETHER);
        this.tag(ModBiomeTags.HAS_ENDER_WISP).addTag(BiomeTags.IS_END);
        this.tag(ModBiomeTags.HAS_PEACOCK_SPIDER).addTag(BiomeTags.IS_BEACH).addTag(BiomeTags.IS_JUNGLE);
    }
}
