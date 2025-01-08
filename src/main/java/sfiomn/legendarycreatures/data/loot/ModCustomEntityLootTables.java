package sfiomn.legendarycreatures.data.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import sfiomn.legendarycreatures.LegendaryCreatures;

import java.util.function.BiConsumer;

public class ModCustomEntityLootTables implements LootTableSubProvider {

    public ModCustomEntityLootTables() {
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {

        biConsumer.accept(
                new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/peacock_spider_level2"),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(Items.STRING)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))
                ));

        biConsumer.accept(
                new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/peacock_spider_level3"),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3))
                        .add(LootItem.lootTableItem(Items.STRING)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))
                ));

        biConsumer.accept(
                new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/scorpion_level2"),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))
                        .add(LootItem.lootTableItem(Items.LEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))
                ));
    }
}
