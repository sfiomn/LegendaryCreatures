package sfiomn.legendarycreatures.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryCreatures.MOD_ID);

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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
