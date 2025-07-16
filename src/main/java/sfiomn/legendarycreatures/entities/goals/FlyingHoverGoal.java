package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;
import sfiomn.legendarycreatures.LegendaryCreatures;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FlyingHoverGoal extends Goal {
    protected final PathfinderMob mob;
    protected final int horizontalDistance;
    protected final int verticalDistance;
    protected final int minHeight;
    protected final int maxHeight;
    private static final int WANDER_THRESHOLD = 22;

    public FlyingHoverGoal(PathfinderMob mob, int horizontalDistance, int verticalDistance, int minHeight, int maxHeight) {
        this.mob = mob;
        this.horizontalDistance = horizontalDistance;
        this.verticalDistance = verticalDistance;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        return this.mob.getNavigation().isDone() && this.mob.getRandom().nextInt(10) == 0;
    }

    public boolean canContinueToUse() {
        return this.mob.getNavigation().isInProgress();
    }

    public void start() {
        Vec3 vec3 = this.findPos();
        if (vec3 != null) {
            this.mob.getNavigation().moveTo(this.mob.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0D);
        }

    }

    @Nullable
    private Vec3 findPos() {
        Vec3 vec3 = this.mob.getViewVector(0.0F);

        Vec3 vec32 = HoverRandomPos.getPos(this.mob, this.horizontalDistance, this.verticalDistance, vec3.x, vec3.z, ((float)Math.PI / 2F), this.maxHeight, this.minHeight);

        return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(this.mob, this.horizontalDistance, this.verticalDistance, -2, vec3.x, vec3.z, (double)((float)Math.PI / 2F));
    }
}
