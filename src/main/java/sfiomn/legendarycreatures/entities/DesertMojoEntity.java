package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.util.WorldUtil;

public class DesertMojoEntity extends MojoEntity {
    private final int baseAttackDuration = 11;
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

    public static void spawn(IWorld world, BlockPos pos, BlockState fromBlockStateSpawn) {
        if (!world.isClientSide()) {
            DesertMojoEntity entityToSpawn = EntityTypeRegistry.DESERT_MOJO.get().create((World) world);
            if (entityToSpawn != null) {
                entityToSpawn.fromBlock(fromBlockStateSpawn);
                WorldUtil.spawnEntity(entityToSpawn, world, pos);
            }
        }
    }
}
