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

    public static final ResourceKey<BiomeModifier> ADD_BULLFROG_DRAGONFLY = registerKey("add_bullfrog_dragonfly");
    public static final ResourceKey<BiomeModifier> ADD_DESERT_MOJO_SCARAB = registerKey("add_desert_mojo_scarab");
    public static final ResourceKey<BiomeModifier> ADD_FOREST_MOJO_BUTTERFLY = registerKey("add_forest_mojo_butterfly");
    public static final ResourceKey<BiomeModifier> ADD_CORPSE_EATER_SCARAB = registerKey("add_corpse_eater_scarab");
    public static final ResourceKey<BiomeModifier> ADD_ENDER_WISP = registerKey("add_ender_wisp");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_WISP = registerKey("add_nether_wisp");
    public static final ResourceKey<BiomeModifier> ADD_WISP_LADYBUG = registerKey("add_wisp_ladybug");
    public static final ResourceKey<BiomeModifier> ADD_HOUND_BUTTERFLY = registerKey("add_hound_butterfly");
    public static final ResourceKey<BiomeModifier> ADD_PEACOCK_SPIDER_MANTIS = registerKey("add_peacock_spider_mantis");
    public static final ResourceKey<BiomeModifier> ADD_PEACOCK_SPIDER_HERMIT_CRAB = registerKey("add_peacock_spider_hermit_crab");
    public static final ResourceKey<BiomeModifier> ADD_SCARECROW_LADYBUG = registerKey("add_scarecrow_ladybug");
    public static final ResourceKey<BiomeModifier> ADD_SCORPION_SCARAB = registerKey("add_scorpion_scarab");
    public static final ResourceKey<BiomeModifier> ADD_BABY_SCORPION_SCARAB = registerKey("add_baby_scorpion_scarab");

    public static final MobSpawnSettings.SpawnerData BULLFROG_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BULLFROG.get(), 20, 2, 2);
    public static final MobSpawnSettings.SpawnerData DESERT_MOJO_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.DESERT_MOJO.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData FOREST_MOJO_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.FOREST_MOJO.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData CORPSE_EATER_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.CORPSE_EATER.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData HOUND_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.HOUND.get(), 20, 1, 2);
    public static final MobSpawnSettings.SpawnerData SCARECROW_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCARECROW.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData SCORPION_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCORPION.get(), 20, 2, 2);
    public static final MobSpawnSettings.SpawnerData WISP_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.WISP.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData NETHER_WISP_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.NETHER_WISP.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData ENDER_WISP_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.ENDER_WISP.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData PEACOCK_SPIDER_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.PEACOCK_SPIDER.get(), 20, 1, 1);
    public static final MobSpawnSettings.SpawnerData SCORPION_BABY_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCORPION_BABY.get(), 20, 1, 1);

    public static final MobSpawnSettings.SpawnerData BUTTERFLY_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BUTTERFLY.get(),40, 1, 1);
    public static final MobSpawnSettings.SpawnerData DRAGONFLY_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.DRAGONFLY.get(),40, 1, 1);
    public static final MobSpawnSettings.SpawnerData LADYBUG_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.LADYBUG.get(),40, 1, 1);
    public static final MobSpawnSettings.SpawnerData SCARAB_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SCARAB.get(),40, 1, 1);
    public static final MobSpawnSettings.SpawnerData MANTIS_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.MANTIS.get(),40, 1, 1);
    public static final MobSpawnSettings.SpawnerData HERMIT_CRAB_SPAWNER_DATA = new MobSpawnSettings.SpawnerData(EntityTypeRegistry.HERMIT_CRAB.get(),40, 1, 1);

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(LegendaryCreatures.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        addSpawn(context, ADD_BULLFROG_DRAGONFLY, biomes.getOrThrow(ModBiomeTags.HAS_BULLFROG_DRAGONFLY), BULLFROG_SPAWNER_DATA, DRAGONFLY_SPAWNER_DATA);

        addSpawn(context, ADD_DESERT_MOJO_SCARAB, biomes.getOrThrow(ModBiomeTags.HAS_DESERT_MOJO_SCARAB), DESERT_MOJO_SPAWNER_DATA, SCARAB_SPAWNER_DATA);

        addSpawn(context, ADD_FOREST_MOJO_BUTTERFLY, biomes.getOrThrow(ModBiomeTags.HAS_FOREST_MOJO_BUTTERFLY), FOREST_MOJO_SPAWNER_DATA, BUTTERFLY_SPAWNER_DATA);

        addSpawn(context, ADD_CORPSE_EATER_SCARAB, biomes.getOrThrow(ModBiomeTags.HAS_CORPSE_EATER_SCARAB), CORPSE_EATER_SPAWNER_DATA, SCARAB_SPAWNER_DATA);

        addSpawn(context, ADD_HOUND_BUTTERFLY, biomes.getOrThrow(ModBiomeTags.HAS_HOUND_BUTTERFLY), HOUND_SPAWNER_DATA, BUTTERFLY_SPAWNER_DATA);

        addSpawn(context, ADD_SCARECROW_LADYBUG, biomes.getOrThrow(ModBiomeTags.HAS_SCARECROW_LADYBUG), SCARECROW_SPAWNER_DATA, LADYBUG_SPAWNER_DATA);

        addSpawn(context, ADD_SCORPION_SCARAB, biomes.getOrThrow(ModBiomeTags.HAS_SCORPION_SCARAB), SCORPION_SPAWNER_DATA, SCARAB_SPAWNER_DATA);

        addSpawn(context, ADD_BABY_SCORPION_SCARAB, biomes.getOrThrow(ModBiomeTags.HAS_BABY_SCORPION_SCARAB), SCORPION_BABY_SPAWNER_DATA, SCARAB_SPAWNER_DATA);

        addSpawn(context, ADD_WISP_LADYBUG, biomes.getOrThrow(ModBiomeTags.HAS_WISP_LADYBUG), WISP_SPAWNER_DATA, LADYBUG_SPAWNER_DATA);

        addSpawn(context, ADD_NETHER_WISP, biomes.getOrThrow(ModBiomeTags.HAS_NETHER_WIP), NETHER_WISP_SPAWNER_DATA);
        addSpawn(context, ADD_ENDER_WISP, biomes.getOrThrow(ModBiomeTags.HAS_ENDER_WISP), ENDER_WISP_SPAWNER_DATA);

        addSpawn(context, ADD_PEACOCK_SPIDER_MANTIS, biomes.getOrThrow(ModBiomeTags.HAS_PEACOCK_SPIDER_MANTIS), PEACOCK_SPIDER_SPAWNER_DATA, MANTIS_SPAWNER_DATA);
        addSpawn(context, ADD_PEACOCK_SPIDER_HERMIT_CRAB, biomes.getOrThrow(ModBiomeTags.HAS_PEACOCK_SPIDER_HERMIT_CRAB), PEACOCK_SPIDER_SPAWNER_DATA, HERMIT_CRAB_SPAWNER_DATA);
    }

    private static void addSpawn(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> resourceName, HolderSet.Named<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        context.register(resourceName, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes, List.of(spawns)));
    }
}
