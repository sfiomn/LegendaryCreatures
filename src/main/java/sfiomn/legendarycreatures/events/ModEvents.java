package sfiomn.legendarycreatures.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.json.JsonBlackLists;
import sfiomn.legendarycreatures.config.json.JsonChanceSpawn;
import sfiomn.legendarycreatures.config.json.JsonConfig;
import sfiomn.legendarycreatures.util.WorldUtil;

import java.util.Map;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = LegendaryCreatures.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
         if (event.getPlayer().isCreative() ||
                 event.getPlayer().isSpectator() ||
                 !event.getState().getBlock().canHarvestBlock(event.getState(), event.getWorld(), event.getPos(), event.getPlayer()))
             return;

        IWorld world = event.getWorld();

        ResourceLocation blockRegistryName = world.getBlockState(event.getPos()).getBlock().getRegistryName();
        if (blockRegistryName == null) {
            return;
        }
        Vector3d pos = new Vector3d(event.getPos().getX() + 0.5, event.getPos().getY(), event.getPos().getZ() + 0.5);

        for (MobEntityEnum mobEntityEnum : MobEntityEnum.values()) {
            String mobId = mobEntityEnum.mobId;
            String blockName = blockRegistryName.toString();
            Set<ResourceLocation> blockTags = world.getBlockState(event.getPos()).getBlock().getTags();

            JsonBlackLists blackLists = JsonConfig.mobIdSpawnList.get(mobId).blackLists;

            Map<String, JsonChanceSpawn> breakingBlockNameSpawns = JsonConfig.mobIdSpawnList.get(mobId).breakingBlockNameSpawns;
            Map<String, JsonChanceSpawn> breakingBlockTagSpawns = JsonConfig.mobIdSpawnList.get(mobId).breakingBlockTagSpawns;

            boolean cancelSpawn = false;
            if (blackLists.breakingBlockNames.contains(blockName)) {
                cancelSpawn = true;
            }
            else
                for (ResourceLocation tag : blockTags) {
                    if (blackLists.breakingBlockTags.contains(tag.toString())) {
                        cancelSpawn = true;
                    }
                }
            if (cancelSpawn)
                continue;

            if (breakingBlockNameSpawns.containsKey(blockRegistryName.toString())) {
                if (spawnEntity(world, pos, mobEntityEnum, breakingBlockNameSpawns.get(blockRegistryName.toString()).chance)) {
                    return;
                }
            } else {
                boolean tagFound = false;
                for (ResourceLocation tag : blockTags) {
                    if (breakingBlockTagSpawns.containsKey(tag.toString())) {
                        tagFound = true;
                        if (spawnEntity(world, pos, mobEntityEnum, breakingBlockTagSpawns.get(tag.toString()).chance)) {
                            return;
                        }
                    }
                    if (!tagFound && breakingBlockNameSpawns.containsKey("default")) {
                        if (spawnEntity(world, pos, mobEntityEnum, breakingBlockNameSpawns.get("default").chance)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            ResourceLocation killedEntityName = event.getEntity().getType().getRegistryName();
            Set<ResourceLocation> killedEntityTags = event.getEntity().getType().getTags();
            if (player.isCreative() || player.isSpectator() || killedEntityName == null)
                return;

            for (MobEntityEnum mobEntityEnum : MobEntityEnum.values()) {
                String mobId = mobEntityEnum.mobId;
                JsonBlackLists blackLists = JsonConfig.mobIdSpawnList.get(mobId).blackLists;

                Map<String, JsonChanceSpawn> killingEntityNameSpawns = JsonConfig.mobIdSpawnList.get(mobId).killingEntityNameSpawns;
                Map<String, JsonChanceSpawn> killingEntityTagSpawns = JsonConfig.mobIdSpawnList.get(mobId).killingEntityTagSpawns;

                boolean cancelSpawn = false;
                if (blackLists.killingEntityNames.contains(killedEntityName.toString())) {
                    cancelSpawn = true;
                }
                else
                    for (ResourceLocation tag : killedEntityTags) {
                        if (blackLists.killingEntityTags.contains(tag.toString())) {
                            cancelSpawn = true;
                        }
                    }
                if (cancelSpawn)
                    continue;

                if (killingEntityNameSpawns.containsKey(killedEntityName.toString())) {
                    if (spawnEntity(event.getEntity().getCommandSenderWorld(), event.getEntity().position(), mobEntityEnum, killingEntityNameSpawns.get(killedEntityName.toString()).chance)) {
                        return;
                    }
                } else {
                    boolean tagFound = false;
                    for (ResourceLocation tag : killedEntityTags) {
                        if (killingEntityTagSpawns.containsKey(tag.toString())) {
                            tagFound = true;
                            if (spawnEntity(event.getEntity().getCommandSenderWorld(), event.getEntity().position(), mobEntityEnum, killingEntityTagSpawns.get(tag.toString()).chance)) {
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

    private static boolean spawnEntity(IWorld world, Vector3d pos, MobEntityEnum mobEntityEnum, double chance) {
        Random rand = world.getRandom();

        if (rand.nextFloat() < chance) {
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(LegendaryCreatures.MOD_ID, mobEntityEnum.mobId));
            if (entityType != null) {
                Entity entityToSpawn = entityType.create((World) world);
                if (entityToSpawn != null) {
                    WorldUtil.spawnEntity(entityToSpawn, world, pos);
                    return true;
                }
            }
        }
        return false;
    }
}
