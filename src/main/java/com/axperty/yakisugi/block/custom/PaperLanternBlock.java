package com.axperty.yakisugi.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PaperLanternBlock extends LanternBlock {

    // Custom voxel shape for paper lanterns
    protected static final VoxelShape AABB = Shapes.or(
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),   // Bottom
            Block.box(4.0D, 1.0D, 4.0D, 12.0D, 14.0D, 12.0D),  // Body
            Block.box(6.0D, 14.0D, 6.0D, 10.0D, 15.0D, 10.0D)  // Top
    );

    // Custom voxel shape for paper lanterns when they're hanging
    protected static final VoxelShape HANGING_AABB = Shapes.or(
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),   // Bottom
            Block.box(4.0D, 1.0D, 4.0D, 12.0D, 14.0D, 12.0D),  // Body
            Block.box(6.0D, 14.0D, 6.0D, 10.0D, 15.0D, 10.0D), // Top
            Block.box(6.5D, 15.0D, 6.5D, 9.5D, 16.0D, 9.5D)    // Hanging chain
    );

    public PaperLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = (double)pos.getX() + 0.5D;
        double yOffset = state.getValue(HANGING) ? 0.3D : 0.7D;
        double d1 = (double)pos.getY() + yOffset;
        double d2 = (double)pos.getZ() + 0.5D;

        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}