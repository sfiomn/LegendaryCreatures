package sfiomn.legendarycreatures.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import sfiomn.legendarycreatures.entities.DesertMojoEntity;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;


public final class WorldUtil {
    public static void spawnEntity(Entity entityToSpawn, IWorld world, BlockPos pos) {
        entityToSpawn.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        //entityToSpawn.setYBodyRot(world.getRandom().nextFloat() * 360F);
        ((MobEntity) entityToSpawn).finalizeSpawn((ServerWorld) world, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()),
                SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
        world.addFreshEntity(entityToSpawn);
    }
}
