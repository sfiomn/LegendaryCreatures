package sfiomn.legendarycreatures.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_BULLFROG_DRAGONFLY = registerKey("has_bullfrog_dragonfly");
    public static final TagKey<Biome> HAS_CORPSE_EATER_SCARAB = registerKey("has_corpse_eater_scarab");
    public static final TagKey<Biome> HAS_DESERT_MOJO_SCARAB = registerKey("has_desert_mojo_scarab");
    public static final TagKey<Biome> HAS_FOREST_MOJO_BUTTERFLY = registerKey("has_forest_mojo_butterfly");
    public static final TagKey<Biome> HAS_ENDER_WISP = registerKey("has_ender_wisp");
    public static final TagKey<Biome> HAS_NETHER_WIP = registerKey("has_nether_wisp");
    public static final TagKey<Biome> HAS_WISP_LADYBUG = registerKey("has_wisp_ladybug");
    public static final TagKey<Biome> HAS_HOUND_BUTTERFLY = registerKey("has_hound_butterfly");
    public static final TagKey<Biome> HAS_PEACOCK_SPIDER_MANTIS = registerKey("has_peacock_spider_mantis");
    public static final TagKey<Biome> HAS_PEACOCK_SPIDER_HERMIT_CRAB = registerKey("has_peacock_spider_hermit_crab");
    public static final TagKey<Biome> HAS_SCARECROW_LADYBUG = registerKey("has_scarecrow_ladybug");
    public static final TagKey<Biome> HAS_SCORPION_SCARAB = registerKey("has_scorpion_scarab");
    public static final TagKey<Biome> HAS_BABY_SCORPION_SCARAB = registerKey("has_baby_scorpion_scarab");

    public static TagKey<Biome> registerKey(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(LegendaryCreatures.MOD_ID, name));
    }
}
