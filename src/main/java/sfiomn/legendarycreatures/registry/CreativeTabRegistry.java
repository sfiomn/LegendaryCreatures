package sfiomn.legendarycreatures.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.LegendaryCreatures;

import java.util.ArrayList;
import java.util.List;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> ITEM_GROUPS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LegendaryCreatures.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LEGENDARY_ADDITIONS_TAB = ITEM_GROUPS.register(LegendaryCreatures.MOD_ID, () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.BULLFROG_SPAWN_EGG.get().getDefaultInstance())
            .displayItems((parameters, list) ->
            {

                List<ItemStack> stacks = new ArrayList<>(List.of(
                        ItemRegistry.BULLFROG_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.SCORPION_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.SCORPION_BABY_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.SCARECROW_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.DESERT_MOJO_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.FOREST_MOJO_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.WISP_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.NETHER_WISP_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.ENDER_WISP_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.HOUND_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.PEACOCK_SPIDER_SPAWN_EGG.get().getDefaultInstance(),
                        ItemRegistry.CORPSE_EATER_SPAWN_EGG.get().getDefaultInstance()
                ));

                list.acceptAll(stacks);
            })
            .title(Component.translatable("itemGroup." + LegendaryCreatures.MOD_ID))
            .build());

    public static void register(IEventBus eventBus) {
        ITEM_GROUPS.register(eventBus);
    }
}
