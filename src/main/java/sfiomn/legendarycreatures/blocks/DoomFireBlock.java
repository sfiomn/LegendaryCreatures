package sfiomn.legendarycreatures.blocks;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;

public class DoomFireBlock extends AbstractFireBlock {
    public DoomFireBlock(Properties properties) {
        super(properties, 1.0f);
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }
}
