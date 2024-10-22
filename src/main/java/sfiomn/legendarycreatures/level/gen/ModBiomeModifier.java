package sfiomn.legendarycreatures.level.gen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.ModBiomeTags;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

import java.util.List;

public class ModBiomeModifier {

    public static final ResourceKey<BiomeModifier> ADD_BULLFROG = registerKey("add_bullfrog");
    public static final ResourceKey<BiomeModifier> ADD_DESERT_MOJO = registerKey("add_desert_mojo");
    public static final ResourceKey<BiomeModifier> ADD_FOREST_MOJO = registerKey("add_forest_mojo");
    public static final ResourceKey<BiomeModifier> ADD_CORPSE_EATER = registerKey("add_corpse_eater");
    public static final ResourceKey<BiomeModifier> ADD_ENDER_WISP = registerKey("add_ender_wisp");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_WISP = registerKey("add_nether_wisp");
    public static final ResourceKey<BiomeModifier> ADD_WISP = registerKey("add_wisp");
    public static final ResourceKey<BiomeModifier> ADD_HOUND = registerKey("add_hound");
    public static final ResourceKey<BiomeModifier> ADD_PEACOCK_SPIDER = registerKey("add_peacock_spider");
    public static final ResourceKey<BiomeModifier> ADD_SCARECROW = registerKey("add_scarecrow");
    public static final ResourceKey<BiomeModifier> ADD_SCORPION = registerKey("add_scorpion");
    public static final ResourceKey<BiomeModifier> ADD_BABY_SCORPION = registerKey("add_baby_scorpion");

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(LegendaryCreatures.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        addSpawn(context, ADD_BULLFROG, biomes.getOrThrow(ModBiomeTags.HAS_BULLFROG),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BULLFROG.get(), 40, 2, 2));
        addSpawn(context, ADD_DESERT_MOJO, biomes.getOrThrow(ModBiomeTags.HAS_DESERT_MOJO),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.DESERT_MOJO.get(), 20, 1, 1));
        addSpawn(context, ADD_FOREST_MOJO, biomes.getOrThrow(ModBiomeTags.HAS_FOREST_MOJO),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.FOREST_MOJO.get(), 20, 1, 1));
        addSpawn(context, ADD_CORPSE_EATER, biomes.getOrThrow(ModBiomeTags.HAS_CORPSE_EATER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.CORPSE_EATER.get(), 20, 1, 1));
        addSpawn(context, ADD_HOUND, biomes.getOrThrow(ModBiomeTags.HAS_HOUND),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.HOUND.get(), 20, 1, 2));
        addSpawn(context, ADD_SCARECROW, biomes.getOrThrow(ModBiomeTags.HAS_SCARECROW),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCARECROW.get(), 20, 1, 1));
        addSpawn(context, ADD_SCORPION, biomes.getOrThrow(ModBiomeTags.HAS_SCORPION),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCORPION.get(), 20, 2, 2));
        addSpawn(context, ADD_WISP, biomes.getOrThrow(ModBiomeTags.HAS_WISP),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.WISP.get(), 5, 1, 1));
        addSpawn(context, ADD_NETHER_WISP, biomes.getOrThrow(ModBiomeTags.HAS_NETHER_WIP),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.NETHER_WISP.get(), 5, 1, 1));
        addSpawn(context, ADD_ENDER_WISP, biomes.getOrThrow(ModBiomeTags.HAS_ENDER_WISP),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.ENDER_WISP.get(), 5, 1, 1));
        addSpawn(context, ADD_PEACOCK_SPIDER, biomes.getOrThrow(ModBiomeTags.HAS_PEACOCK_SPIDER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.PEACOCK_SPIDER.get(), 20, 1, 1));
        addSpawn(context, ADD_BABY_SCORPION, biomes.getOrThrow(ModBiomeTags.HAS_BABY_SCORPION),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCORPION_BABY.get(), 20, 1, 1));
    }

    private static void addSpawn(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> resourceName, HolderSet.Named<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        context.register(resourceName, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes, List.of(spawns)));
    }
}
