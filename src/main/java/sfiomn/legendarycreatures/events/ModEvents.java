package sfiomn.legendarycreatures.events;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;
import sfiomn.legendarycreatures.config.json.JsonBreakingBlockSpawn;
import sfiomn.legendarycreatures.config.json.JsonConfig;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber(modid = LegendaryCreatures.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
         if (event.getPlayer().isCreative() ||
                 event.getPlayer().isSpectator() ||
                 !event.getState().getBlock().canHarvestBlock(event.getState(), event.getWorld(), event.getPos(), event.getPlayer()))
             return;

        IWorld world = event.getWorld();

        Random rand = world.getRandom();

        BlockState fromBlockStateSpawn = world.getBlockState(event.getPos());
        ResourceLocation blockRegistryName = fromBlockStateSpawn.getBlock().getRegistryName();
        if (blockRegistryName == null) {
            return;
        }

        for (String mobId : JsonConfig.mobIdSpawnList.keySet()) {
            MobEntityEnum entityEnum = MobEntityEnum.valueOfMobId(mobId);
            if (entityEnum == null)
                continue;

            Map<String, JsonBreakingBlockSpawn> breakingBlockNameSpawns = JsonConfig.mobIdSpawnList.get(mobId).breakingBlockNameSpawns;
            Map<String, JsonBreakingBlockSpawn> breakingBlockTagSpawns = JsonConfig.mobIdSpawnList.get(mobId).breakingBlockTagSpawns;

            if (breakingBlockNameSpawns.containsKey(blockRegistryName.toString())) {
                if (rand.nextFloat() < breakingBlockNameSpawns.get(blockRegistryName.toString()).chance) {
                    Class<? extends AnimatedCreatureEntity> entityClass = entityEnum.entityConstructor;
                    spawnEntity(world, event.getPos(), entityClass, fromBlockStateSpawn);
                    return;
                }
            } else {
                for (ResourceLocation tag : world.getBlockState(event.getPos()).getBlock().getTags()) {
                    if (breakingBlockTagSpawns.containsKey(tag.toString())) {
                        if (rand.nextFloat() < breakingBlockTagSpawns.get(tag.toString()).chance) {
                            Class<? extends AnimatedCreatureEntity> entityClass = entityEnum.entityConstructor;
                            spawnEntity(world, event.getPos(), entityClass, fromBlockStateSpawn);
                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof PlayerEntity)
            LegendaryCreatures.LOGGER.debug("Player killed " + event.getEntity().getName());
    }

    private static void spawnEntity(IWorld world, BlockPos pos, Class<? extends AnimatedCreatureEntity> classEntity, BlockState fromBlockStateSpawn) {
        try {
            Method spawnMethod = classEntity.getMethod("spawn", IWorld.class, BlockPos.class, BlockState.class);
            Object[] parametersArray = new Object[] { world, pos, fromBlockStateSpawn };
            spawnMethod.invoke(null, parametersArray);
        } catch (NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException ignored) {
        }
    }
}
