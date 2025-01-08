package sfiomn.legendarycreatures.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import sfiomn.legendarycreatures.data.loot.ModCustomEntityLootTables;
import sfiomn.legendarycreatures.data.loot.ModEntityLootTables;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider {
    public static LootTableProvider createLootTables(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ModCustomEntityLootTables::new, LootContextParamSets.ENTITY)
        ));
    }
}
