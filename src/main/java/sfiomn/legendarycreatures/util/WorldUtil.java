package sfiomn.legendarycreatures.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;


public final class WorldUtil {
    public static void spawnEntity(Entity entityToSpawn, LevelAccessor world, Vec3 pos) {
        entityToSpawn.setPos(pos.x, pos.y, pos.z);
        //entityToSpawn.setYBodyRot(world.getRandom().nextFloat() * 360F);
        ((Mob) entityToSpawn).finalizeSpawn((ServerLevelAccessor) world, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        world.addFreshEntity(entityToSpawn);
    }

    public static float rotateTowards(float currentRotation, float wantedRotation, float maxRotationInDegree) {
        float f = Mth.degreesDifference(currentRotation, wantedRotation);
        float f1 = Mth.clamp(f, -maxRotationInDegree, maxRotationInDegree);
        return currentRotation + f1;
    }

    public static float getYRotD(LivingEntity entity, double wantedPosX, double wantedPosZ) {
        double d0 = wantedPosX - entity.getX();
        double d1 = wantedPosZ - entity.getZ();
        return (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }
}
