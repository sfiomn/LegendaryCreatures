package sfiomn.legendarycreatures.blocks;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import sfiomn.legendarycreatures.registry.BlockRegistry;

public class DoomFireBlock extends AbstractFireBlock {
    public DoomFireBlock(Properties properties) {
        super(properties, 1.0f);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState newBlockState, IWorld world, BlockPos blockPos, BlockPos newBlockPos) {
        return this.canSurvive(blockState, world, blockPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    public boolean canSurvive(BlockState blockState, IWorldReader world, BlockPos blockPos) {
        return checkDoomFireSurvive(blockState, world, blockPos);
    }

    public static boolean checkDoomFireSurvive(BlockState blockState, IWorldReader world, BlockPos blockPos) {
        return canSurviveOnBlock(world.getBlockState(blockPos.below()).getBlock()) &&
                blockState.isFaceSturdy(world, blockPos.below(), Direction.UP);
    }

    public static boolean canSurviveOnBlock(Block block) {
        return !block.is(Blocks.AIR) &&
                !block.is(Blocks.FIRE) &&
                !block.is(Blocks.SOUL_FIRE) &&
                !block.is(BlockRegistry.DOOM_FIRE_BLOCK.get()) &&
                !block.is(Blocks.WATER);
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }
}
