package sfiomn.legendarycreatures.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import sfiomn.legendarycreatures.registry.ItemRegistry;

public class ModItemGroup {

    public static final ItemGroup LEGENDARY_CREATURES_GROUP = new ItemGroup("legendary_creatures") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.SCARECROW_SPAWN_EGG.get());
        }
    };
}
