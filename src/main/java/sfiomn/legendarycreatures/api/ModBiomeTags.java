package sfiomn.legendarycreatures.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_BULLFROG = registerKey("has_bullfrog");
    public static final TagKey<Biome> HAS_CORPSE_EATER = registerKey("has_corpse_eater");
    public static final TagKey<Biome> HAS_DESERT_MOJO = registerKey("has_desert_mojo");
    public static final TagKey<Biome> HAS_FOREST_MOJO = registerKey("has_forest_mojo");
    public static final TagKey<Biome> HAS_ENDER_WISP = registerKey("has_ender_wisp");
    public static final TagKey<Biome> HAS_NETHER_WIP = registerKey("has_nether_wisp");
    public static final TagKey<Biome> HAS_WISP = registerKey("has_wisp");
    public static final TagKey<Biome> HAS_HOUND = registerKey("has_hound");
    public static final TagKey<Biome> HAS_PEACOCK_SPIDER = registerKey("has_peacock_spider");
    public static final TagKey<Biome> HAS_SCARECROW = registerKey("has_scarecrow");
    public static final TagKey<Biome> HAS_SCORPION = registerKey("has_scorpion");
    public static final TagKey<Biome> HAS_BABY_SCORPION = registerKey("has_baby_scorpion");

    public static TagKey<Biome> registerKey(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(LegendaryCreatures.MOD_ID, name));
    }
}
