package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import sfiomn.legendarycreatures.LegendaryCreatures;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class FleeAirGoal<T extends LivingEntity> extends Goal {

    protected final PathfinderMob mob;
    protected final double walkSpeedModifier;
    protected final double sprintSpeedModifier;
    @Nullable
    protected T toAvoid;
    protected final float maxDist;
    @Nullable
    protected final Class<T> avoidClass;
    protected Path path;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;

    public FleeAirGoal(PathfinderMob mob, Class<T> avoidClass, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
        this(mob, avoidClass, (p_25052_) -> true, maxDist, walkSpeedModifier, sprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    FleeAirGoal(PathfinderMob mob, @org.jetbrains.annotations.Nullable Class<T> avoidClass, Predicate<LivingEntity> avoidPredicate, float maxDist, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
        this.mob = mob;
        this.avoidClass = avoidClass;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = maxDist;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.predicateOnAvoidEntity = predicateOnAvoidEntity;
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDist).selector(predicateOnAvoidEntity.and(avoidPredicate));
    }

    public boolean canUse() {
        if (this.avoidClass == null)
            return false;

        this.toAvoid = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate((double)this.maxDist, 15.0, (double)this.maxDist), (p_148078_) -> true), this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());

        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vecToAvoid = this.mob.position().subtract(this.toAvoid.position());
            BlockPos relativePosAway = RandomPos.generateRandomDirectionWithinRadians(this.mob.getRandom(), 20, 3, 0, vecToAvoid.x, vecToAvoid.z, 0.785398185253);

            if (relativePosAway == null) {
                return false;
            } else {
                BlockPos posAway = RandomPos.generateRandomPosTowardDirection(this.mob, 20, this.mob.getRandom(), relativePosAway);

                if (this.toAvoid.distanceToSqr(posAway.getCenter()) < this.toAvoid.distanceToSqr(this.mob)) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().createPath(posAway.getCenter().x, posAway.getCenter().y, posAway.getCenter().z, 0);
                    return this.path != null;
                }
            }
        }
    }

    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.walkSpeedModifier);
    }

    public void stop() {
        this.toAvoid = null;
        this.mob.getNavigation().stop();
    }

    public void tick() {
        if (this.toAvoid == null)
            return;

        if (this.mob.distanceToSqr(this.toAvoid) < 49.0) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }

    }
}
