package sfiomn.legendarycreatures.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import sfiomn.legendarycreatures.entities.DesertMojoEntity;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;


public final class WorldUtil {
    public static void spawnEntity(Entity entityToSpawn, IWorld world, Vector3d pos) {
        entityToSpawn.setPos(pos.x, pos.y, pos.z);
        //entityToSpawn.setYBodyRot(world.getRandom().nextFloat() * 360F);
        ((MobEntity) entityToSpawn).finalizeSpawn((ServerWorld) world, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()),
                SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
        world.addFreshEntity(entityToSpawn);
    }

    public static float rotateTowards(float currentRotation, float wantedRotation, float maxRotationInDegree) {
        float f = MathHelper.degreesDifference(currentRotation, wantedRotation);
        float f1 = MathHelper.clamp(f, -maxRotationInDegree, maxRotationInDegree);
        return currentRotation + f1;
    }

    public static float getYRotD(LivingEntity entity, double wantedPosX, double wantedPosZ) {
        double d0 = wantedPosX - entity.getX();
        double d1 = wantedPosZ - entity.getZ();
        return (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }
}
