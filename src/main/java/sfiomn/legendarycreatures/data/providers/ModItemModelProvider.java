package sfiomn.legendarycreatures.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.registry.ItemRegistry;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LegendaryCreatures.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        spawnEgg(ItemRegistry.BULLFROG_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.CORPSE_EATER_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.DESERT_MOJO_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.FOREST_MOJO_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.ENDER_WISP_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.NETHER_WISP_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.WISP_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.HOUND_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.PEACOCK_SPIDER_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.SCARECROW_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.SCORPION_SPAWN_EGG.get());
        spawnEgg(ItemRegistry.SCORPION_BABY_SPAWN_EGG.get());
    }

    private void spawnEgg(Item item) {
        ResourceLocation itemRegistryName = ForgeRegistries.ITEMS.getKey(item);
        if (itemRegistryName != null)
            this.withExistingParent(itemRegistryName.toString(), this.mcLoc("item/template_spawn_egg"));
    }
}
