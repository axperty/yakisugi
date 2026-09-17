package com.axperty.yakisugi.block.custom;

import com.axperty.yakisugi.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

public class AgedStoneLanternTopBlock extends Block {
    // Oh dear this took some time to properly render

    // Lantern Types
    public static final int EMPTY = 0;
    public static final int NORMAL_LANTERN = 1;
    public static final int SOUL_LANTERN = 2;
    public static final IntegerProperty LANTERN_TYPE = IntegerProperty.create("lantern_type", EMPTY, SOUL_LANTERN);

    // Custom voxel shape for lantern top
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(4.0D, 4.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.box(4.0D, 6.0D, 10.0D, 6.0D, 10.0D, 12.0D),
            Block.box(4.0D, 6.0D, 4.0D, 6.0D, 10.0D, 6.0D),
            Block.box(10.0D, 6.0D, 10.0D, 12.0D, 10.0D, 12.0D),
            Block.box(10.0D, 6.0D, 4.0D, 12.0D, 10.0D, 6.0D),
            Block.box(4.0D, 10.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.box(1.0D, 12.0D, 1.0D, 15.0D, 16.0D, 15.0D)
    );

    public AgedStoneLanternTopBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LANTERN_TYPE, EMPTY));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(BlockRegistry.AGED_STONE_LANTERN) || below.is(BlockRegistry.POLISHED_AGED_STONE_LANTERN);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LANTERN_TYPE, EMPTY);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LANTERN_TYPE);
    }

    // Allow to use a lantern inside the aged stone lantern
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int currentType = state.getValue(LANTERN_TYPE);

        if (currentType == EMPTY) {
            int newType = EMPTY;
            if (stack.is(Items.LANTERN)) {
                newType = NORMAL_LANTERN;
            } else if (stack.is(Items.SOUL_LANTERN)) {
                newType = SOUL_LANTERN;
            }

            if (newType != EMPTY) {
                if (!level.isClientSide) {
                    level.setBlock(pos, state.setValue(LANTERN_TYPE, newType), 3);
                    level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if (stack.isEmpty() && hand == InteractionHand.MAIN_HAND) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(LANTERN_TYPE, EMPTY), 3);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    Block.popResource(level, pos, new ItemStack(currentType == SOUL_LANTERN ? Items.SOUL_LANTERN : Items.LANTERN));
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static int getLightEmission(BlockState state) {
        int type = state.getValue(LANTERN_TYPE);
        if (type == NORMAL_LANTERN) return 15;
        if (type == SOUL_LANTERN) return 10;
        return 0;
    }
}
