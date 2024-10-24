package sfiomn.legendarycreatures.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.registry.BlockRegistry;


public class DoomFireBlock extends BaseFireBlock {
    public static final int MAX_AGE = 15;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    public DoomFireBlock(BlockBehaviour.Properties properties) {
        super(properties, 2.0f);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.scheduleTick(pos, this, getFireTickDelay(level.random));

        if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            if (!blockState.canSurvive(level, pos)) {
                level.removeBlock(pos, false);
            }

            int i = blockState.getValue(AGE);

            int j = Math.min(MAX_AGE, i + rand.nextInt(3) / 2);

            if (i != j) {
                blockState = blockState.setValue(AGE, j);
                level.setBlock(pos, blockState, 4);
            }

            if (i == MAX_AGE && rand.nextInt(4) == 0) {
                level.removeBlock(pos, false);
            }
        }
    }

    private static int getFireTickDelay(RandomSource rand) {
        return 30 + rand.nextInt(10);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos pos, BlockState newBlockState, boolean p_49283_) {
        super.onPlace(blockState, level, pos, newBlockState, p_49283_);
        level.scheduleTick(pos, this, getFireTickDelay(level.random));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader level, @NotNull BlockPos blockPos) {
        return checkDoomFireSurvive(level.getBlockState(blockPos.below()), level, blockPos.below());
    }

    public static boolean checkDoomFireSurvive(BlockState blockState, LevelReader world, BlockPos blockPos) {
        return canSurviveOnBlock(blockState) &&
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
