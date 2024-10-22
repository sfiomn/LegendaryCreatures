package sfiomn.legendarycreatures.data.providers;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.registry.BlockRegistry;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LegendaryCreatures.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        fireBlock(BlockRegistry.DOOM_FIRE_BLOCK.get(), "doom_fire");
    }

    public void fireBlock(Block block, String baseName){
        ResourceLocation fire0 = this.modLoc("block/" + baseName + "_0");
        ResourceLocation fire1 = this.modLoc("block/" + baseName + "_1");
        ModelFile floor0 = this.models().singleTexture(baseName + "_floor0", this.mcLoc("block/template_fire_floor"), "fire", fire0).renderType("cutout");
        ModelFile floor1 = this.models().singleTexture(baseName + "_floor1", this.mcLoc("block/template_fire_floor"), "fire", fire1).renderType("cutout");
        ModelFile side0 = this.models().singleTexture(baseName + "_side0", this.mcLoc("block/template_fire_side"), "fire", fire0).renderType("cutout");
        ModelFile side1 = this.models().singleTexture(baseName + "_side1", this.mcLoc("block/template_fire_side"), "fire", fire1).renderType("cutout");
        ModelFile sideAlt0 = this.models().singleTexture(baseName + "_side_alt0", this.mcLoc("block/template_fire_side_alt"), "fire", fire0).renderType("cutout");
        ModelFile sideAlt1 = this.models().singleTexture(baseName + "_side_alt1", this.mcLoc("block/template_fire_side_alt"), "fire", fire1).renderType("cutout");
        ModelFile up0 = this.models().singleTexture(baseName + "_up0", this.mcLoc("block/template_fire_up"), "fire", fire0).renderType("cutout");
        ModelFile up1 = this.models().singleTexture(baseName + "_up1", this.mcLoc("block/template_fire_up"), "fire", fire1).renderType("cutout");
        ModelFile upAlt0 = this.models().singleTexture(baseName + "_up_alt0", this.mcLoc("block/template_fire_up_alt"), "fire", fire0).renderType("cutout");
        ModelFile upAlt1 = this.models().singleTexture(baseName + "_up_alt1", this.mcLoc("block/template_fire_up_alt"), "fire", fire1).renderType("cutout");

        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);

        builder
                .part()
                .modelFile(floor0)
                .nextModel()
                .modelFile(floor1)
                .addModel()
                .end();

        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                int rotValue = dir == Direction.WEST ? 270 : (dir == Direction.SOUTH ? 180 : (dir == Direction.EAST ? 90 : 0));
                builder
                        .part()
                        .modelFile(side0)
                        .rotationY(rotValue)
                        .nextModel()
                        .modelFile(side1)
                        .rotationY(rotValue)
                        .nextModel()
                        .modelFile(sideAlt0)
                        .rotationY(rotValue)
                        .nextModel()
                        .modelFile(sideAlt1)
                        .rotationY(rotValue)
                        .addModel()
                        .end();
            }
        });
    }
}