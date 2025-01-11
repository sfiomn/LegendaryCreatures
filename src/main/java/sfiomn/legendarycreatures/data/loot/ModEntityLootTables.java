package sfiomn.legendarycreatures.data.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.ItemRegistry;

import java.util.stream.Stream;


public class ModEntityLootTables extends EntityLootSubProvider {

    public ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.add(EntityTypeRegistry.SCARECROW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.STRAW_HAT.get())
                            .when(LootItemRandomChanceCondition.randomChance(0.1f))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.WHEAT)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0f, 20.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(3.0f, 7.0f)))))
                .withPool(LootPool.lootPool()
                        .add(TagEntry.expandTag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.BULLFROG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.SLIME_BALL)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.CORPSE_EATER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.BONE)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0f, 8.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(3.0f, 6.0f))))));

        this.add(EntityTypeRegistry.DESERT_MOJO.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.CACTUS)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 3.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.FOREST_MOJO.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.ROSE_BUSH)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))
                        .add(LootItem.lootTableItem(Items.LILAC)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))
                        .add(TagEntry.expandTag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                            .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.HOUND.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.LEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0f, 5.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.PEACOCK_SPIDER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.STRING)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.SCORPION.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0f, 2.0f))))));

        this.add(EntityTypeRegistry.SCORPION_BABY.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .apply(LootingEnchantFunction.lootingMultiplier(ConstantValue.exactly(1))))));

        this.add(EntityTypeRegistry.WISP.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(EmptyLootItem.emptyItem())));
        this.add(EntityTypeRegistry.NETHER_WISP.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(EmptyLootItem.emptyItem())));
        this.add(EntityTypeRegistry.ENDER_WISP.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(EmptyLootItem.emptyItem())));
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityTypeRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
