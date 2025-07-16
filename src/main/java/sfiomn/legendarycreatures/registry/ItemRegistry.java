package sfiomn.legendarycreatures.registry;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.items.StrawHatItem;

import static sfiomn.legendarycreatures.items.materials.ArmorMaterialBase.STRAW;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<Item> STRAW_HAT = ITEMS.register("straw_hat", () -> new StrawHatItem(STRAW, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<SpawnEggItem> DESERT_MOJO_SPAWN_EGG = ITEMS.register("desert_mojo_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.DESERT_MOJO, -13210, -52,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> FOREST_MOJO_SPAWN_EGG = ITEMS.register("forest_mojo_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.FOREST_MOJO, -6684775, -3368449,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> HOUND_SPAWN_EGG = ITEMS.register("hound_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.HOUND, -10079488, -10066330,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> SCARECROW_SPAWN_EGG = ITEMS.register("scarecrow_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.SCARECROW, -3368704, -205,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> SCORPION_SPAWN_EGG = ITEMS.register("scorpion_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.SCORPION, -6654645, -277130,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> SCORPION_BABY_SPAWN_EGG = ITEMS.register("scorpion_baby_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.SCORPION_BABY, -6654645, -277130,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> WISP_SPAWN_EGG = ITEMS.register("wisp_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.WISP, -10289350, -10533588,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> NETHER_WISP_SPAWN_EGG = ITEMS.register("nether_wisp_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.NETHER_WISP, 0xba3519, 0x393939,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> ENDER_WISP_SPAWN_EGG = ITEMS.register("ender_wisp_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.ENDER_WISP, 0xfe95e0, 0xe86ad6,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> CORPSE_EATER_SPAWN_EGG = ITEMS.register("corpse_eater_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CORPSE_EATER, 0x0F1F27, 0x137FC2,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> PEACOCK_SPIDER_SPAWN_EGG = ITEMS.register("peacock_spider_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACOCK_SPIDER, 0x0964aa, 0xac3033,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> BULLFROG_SPAWN_EGG = ITEMS.register("bullfrog_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BULLFROG, 0x7b9a24, 0x7a1f1d,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> BUTTERFLY_SPAWN_EGG = ITEMS.register("butterfly_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY, 0xe65b89, 0xfb8e9c,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> DRAGONFLY_SPAWN_EGG = ITEMS.register("dragonfly_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.DRAGONFLY, 0x5bdcc5, 0xd8d8d8,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> LADYBUG_SPAWN_EGG = ITEMS.register("ladybug_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.LADYBUG, 0x2c1418, 0xc12528,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> SCARAB_SPAWN_EGG = ITEMS.register("scarab_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.SCARAB, 0x3bd77d, 0x298d7c,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> MANTIS_SPAWN_EGG = ITEMS.register("mantis_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.MANTIS, 0x56ab1a, 0xdbdbdb,
                    new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> HERMIT_CRAB_SPAWN_EGG = ITEMS.register("hermit_crab_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.HERMIT_CRAB, 0xf89a22, 0xe3d4cb,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
