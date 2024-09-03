package sfiomn.legendarycreatures.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.registry.BlockRegistry;

public class DoomFireBlock extends FireBlock {
    public DoomFireBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader level, @NotNull BlockPos blockPos) {
        return checkDoomFireSurvive(blockState, level, blockPos);
    }

    public static boolean checkDoomFireSurvive(BlockState blockState, LevelReader world, BlockPos blockPos) {
        return canSurviveOnBlock(world.getBlockState(blockPos.below())) &&
                blockState.isFaceSturdy(world, blockPos.below(), Direction.UP);
    }

    public static boolean canSurviveOnBlock(BlockState blockState) {
        return !blockState.is(Blocks.AIR) &&
                !blockState.is(Blocks.FIRE) &&
                !blockState.is(Blocks.SOUL_FIRE) &&
                !blockState.is(BlockRegistry.DOOM_FIRE_BLOCK.get()) &&
                !blockState.is(Blocks.WATER);
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }
}
