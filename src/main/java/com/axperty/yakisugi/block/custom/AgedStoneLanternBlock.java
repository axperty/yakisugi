package com.axperty.yakisugi.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

public class AgedStoneLanternBlock extends Block {
    // Custom voxel shape for lantern base
    private static final VoxelShape BASE_SHAPE = Shapes.or(
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D),
            Block.box(4.0D, 4.0D, 4.0D, 12.0D, 16.0D, 12.0D)
    );

    public AgedStoneLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BASE_SHAPE;
    }
}
