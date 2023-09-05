package sfiomn.legendarycreatures.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.blocks.DoomFireBlock;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LegendaryCreatures.MOD_ID);
    public static final RegistryObject<Block> DOOM_FIRE = BLOCKS.register("doom_fire", () -> new DoomFireBlock(AbstractBlock.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_BLUE).noCollission().instabreak().lightLevel((p_235468_0_) -> {
        return 15;
    }).sound(SoundType.WOOL)));

    private static <T extends Block> RegistryObject<Block> registerBlock(String name, Supplier<T> block) {
        RegistryObject<Block> newBlock = BLOCKS.register(name, block);
        registerBlockItem(name, newBlock);
        return newBlock;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
