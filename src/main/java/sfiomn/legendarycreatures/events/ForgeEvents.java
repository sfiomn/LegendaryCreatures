package sfiomn.legendarycreatures.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.BlackLists;
import sfiomn.legendarycreatures.config.json.JsonChanceSpawn;
import sfiomn.legendarycreatures.config.json.JsonConfig;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;
import sfiomn.legendarycreatures.util.WorldUtil;

import java.util.*;

@Mod.EventBusSubscriber(modid = LegendaryCreatures.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
         if (event.getPlayer().isCreative() ||
                 event.getPlayer().isSpectator() ||
                 !event.getState().getBlock().canHarvestBlock(event.getState(), event.getLevel(), event.getPos(), event.getPlayer()))
             return;

        LevelAccessor level = event.getLevel();

        ResourceLocation blockRegistryName = ForgeRegistries.BLOCKS.getKey(event.getState().getBlock());
        if (blockRegistryName == null) {
            return;
        }
        Vec3 pos = new Vec3(event.getPos().getX() + 0.5, event.getPos().getY(), event.getPos().getZ() + 0.5);

        Optional<IReverseTag<Block>> blockTagsOptional = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getReverseTag(event.getState().getBlock());
        IReverseTag<Block> blockTags = null;
        if (blockTagsOptional.isPresent())
            blockTags = blockTagsOptional.get();

        for (MobEntityEnum mobEntityEnum : MobEntityEnum.values()) {
            String mobId = mobEntityEnum.mobId;
            String blockName = blockRegistryName.toString();

            BlackLists blackLists = JsonConfig.mobIdSpawnList.get(mobId).blackLists;

            Map<String, JsonChanceSpawn> breakingBlockNameSpawns = JsonConfig.mobIdSpawnList.get(mobId).breakingBlockNameSpawns;
            Map<TagKey<Block>, JsonChanceSpawn> breakingBlockTagSpawns = JsonConfig.mobIdSpawnList.get(mobId).breakingBlockTagSpawns;

            boolean cancelSpawn = false;
            if (blackLists.breakingBlockNames.contains(blockName)) {
                cancelSpawn = true;
            }
            else if (blockTags != null) {
                for (TagKey<Block> blackListedBlockTag : blackLists.breakingBlockTags) {
                    if (blockTags.containsTag(blackListedBlockTag)) {
                        cancelSpawn = true;
                    }
                }
            }
            if (cancelSpawn)
                continue;

            if (breakingBlockNameSpawns.containsKey(blockRegistryName.toString())) {
                if (spawnEntity(level, pos, mobEntityEnum, breakingBlockNameSpawns.get(blockRegistryName.toString()).chance)) {
                    return;
                }
            } else if (blockTags != null) {
                boolean tagFound = false;
                for (TagKey<Block> spawnBlockTag : breakingBlockTagSpawns.keySet()) {
                    if (blockTags.containsTag(spawnBlockTag)) {
                        tagFound = true;
                        if (spawnEntity(level, pos, mobEntityEnum, breakingBlockTagSpawns.get(spawnBlockTag).chance)) {
                            return;
                        }
                    }
                    if (!tagFound && breakingBlockNameSpawns.containsKey("default")) {
                        if (spawnEntity(level, pos, mobEntityEnum, breakingBlockNameSpawns.get("default").chance)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ResourceLocation killedEntityName = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());

            Optional<IReverseTag<EntityType<?>>> entityTypeTagsOptional = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getReverseTag(event.getEntity().getType());
            IReverseTag<EntityType<?>> entityTypeTags = null;
            if (entityTypeTagsOptional.isPresent())
                entityTypeTags = entityTypeTagsOptional.get();

            if (player.isCreative() || player.isSpectator() || killedEntityName == null)
                return;

            for (MobEntityEnum mobEntityEnum : MobEntityEnum.values()) {
                String mobId = mobEntityEnum.mobId;
                BlackLists blackLists = JsonConfig.mobIdSpawnList.get(mobId).blackLists;

                Map<String, JsonChanceSpawn> killingEntityNameSpawns = JsonConfig.mobIdSpawnList.get(mobId).killingEntityNameSpawns;
                Map<TagKey<EntityType<?>>, JsonChanceSpawn> killingEntityTagSpawns = JsonConfig.mobIdSpawnList.get(mobId).killingEntityTypeTagSpawns;

                boolean cancelSpawn = false;
                if (blackLists.killingEntityNames.contains(killedEntityName.toString())) {
                    cancelSpawn = true;
                }
                else if (entityTypeTags != null)
                    for (TagKey<EntityType<?>> blackListedEntityTypeTag : blackLists.killingEntityTypeTags) {
                        if (entityTypeTags.containsTag(blackListedEntityTypeTag)) {
                            cancelSpawn = true;
                        }
                    }
                if (cancelSpawn)
                    continue;

                if (killingEntityNameSpawns.containsKey(killedEntityName.toString())) {
                    if (spawnEntity(event.getEntity().getCommandSenderWorld(), event.getEntity().position(), mobEntityEnum, killingEntityNameSpawns.get(killedEntityName.toString()).chance)) {
                        return;
                    }
                } else if (entityTypeTags != null) {
                    boolean tagFound = false;
                    for (TagKey<EntityType<?>> spawnKillingEntityTypeTag: killingEntityTagSpawns.keySet()) {
                        if (entityTypeTags.containsTag(spawnKillingEntityTypeTag)) {
                            tagFound = true;
                            if (spawnEntity(event.getEntity().getCommandSenderWorld(), event.getEntity().position(), mobEntityEnum, killingEntityTagSpawns.get(spawnKillingEntityTypeTag).chance)) {
                                return;
                            }
                        }
                    }
                    if (!tagFound && killingEntityNameSpawns.containsKey("default")) {
                        if (spawnEntity(event.getEntity().getCommandSenderWorld(), event.getEntity().position(), mobEntityEnum, killingEntityNameSpawns.get("default").chance)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private static boolean spawnEntity(LevelAccessor level, Vec3 pos, MobEntityEnum mobEntityEnum, double chance) {
        RandomSource rand = level.getRandom();

        if (rand.nextFloat() < chance) {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(LegendaryCreatures.MOD_ID, mobEntityEnum.mobId));
            if (entityType != null) {
                Entity entityToSpawn = entityType.create((Level) level);
                if (entityToSpawn != null) {
                    if (entityToSpawn instanceof AnimatedCreatureEntity animEntity)
                        animEntity.setSpawnEffect(true);
                    WorldUtil.spawnEntity(entityToSpawn, level, pos);
                    return true;
                }
            }
        }
        return false;
    }
}
