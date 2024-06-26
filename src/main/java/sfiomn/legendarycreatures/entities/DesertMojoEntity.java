package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.util.WorldUtil;

public class DesertMojoEntity extends MojoEntity {
    private final int baseAttackDuration = 11;
    private final int baseAttackActionPoint = 5;
    public DesertMojoEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        setVariant(0);
    }

    @Override
    protected int getBaseAttackDuration() {
        return baseAttackDuration;
    }

    @Override
    protected int getBaseAttackActionPoint() {
        return baseAttackActionPoint;
    }
}
