package com.axperty.yakisugi.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.LevelAccessor;

public class ShojiDoorBlock extends DoorBlock {
    // This custom door has 3 stages in total, unlike regular doors
    // Meaning there will be 3 different shapes
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 2);

    protected static final VoxelShape CLOSED_X = Block.box(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    protected static final VoxelShape CLOSED_Z = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);

    protected static final VoxelShape OPEN_X_LEFT_STAGE_1 = Block.box(0.0D, 0.0D, 7.0D, 8.0D, 16.0D, 9.0D);
    protected static final VoxelShape OPEN_X_RIGHT_STAGE_1 = Block.box(8.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    protected static final VoxelShape OPEN_Z_LEFT_STAGE_1 = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 8.0D);
    protected static final VoxelShape OPEN_Z_RIGHT_STAGE_1 = Block.box(7.0D, 0.0D, 8.0D, 9.0D, 16.0D, 16.0D);

    protected static final VoxelShape OPEN_X_LEFT_STAGE_2 = Block.box(0.0D, 0.0D, 7.0D, 5.0D, 16.0D, 9.0D);
    protected static final VoxelShape OPEN_X_RIGHT_STAGE_2 = Block.box(11.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    protected static final VoxelShape OPEN_Z_LEFT_STAGE_2 = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 5.0D);
    protected static final VoxelShape OPEN_Z_RIGHT_STAGE_2 = Block.box(7.0D, 0.0D, 11.0D, 9.0D, 16.0D, 16.0D);

    public ShojiDoorBlock(BlockSetType type, Properties properties) {
        super(type, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!this.type().canOpenByHand()) {
            return InteractionResult.PASS;
        } else {
            int currentStage = state.getValue(STAGE);
            int nextStage = (currentStage + 1) % 3;
            boolean nextOpen = nextStage > 0;
            
            state = state.setValue(STAGE, nextStage).setValue(OPEN, nextOpen);
            level.setBlock(pos, state, 10);
            
            if (currentStage == 0 && nextStage == 1) {
                level.playSound(player, pos, this.type().doorOpen(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            } else if (currentStage == 2 && nextStage == 0) {
                level.playSound(player, pos, this.type().doorClose(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            } else if (currentStage == 1 && nextStage == 2) {
                level.playSound(player, pos, this.type().doorOpen(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 1.1F);
            }
            
            level.gameEvent(player, nextOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.is(this) && neighborState.getValue(HALF) != half ? state.setValue(STAGE, neighborState.getValue(STAGE)).setValue(OPEN, neighborState.getValue(OPEN)).setValue(HINGE, neighborState.getValue(HINGE)).setValue(POWERED, neighborState.getValue(POWERED)) : net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        } else {
            return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, currentPos) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean hasSignal = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.relative(state.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (!this.defaultBlockState().is(block) && hasSignal != state.getValue(POWERED)) {
            if (hasSignal != state.getValue(OPEN)) {
                level.playSound(null, pos, hasSignal ? this.type().doorOpen() : this.type().doorClose(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                level.gameEvent(null, hasSignal ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }

            level.setBlock(pos, state.setValue(POWERED, hasSignal).setValue(OPEN, hasSignal).setValue(STAGE, hasSignal ? 2 : 0), 2);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        int stage = state.getValue(STAGE);
        DoorHingeSide hinge = state.getValue(HINGE);

        if (stage == 0) {
            return switch (direction) {
                case EAST, WEST -> CLOSED_Z;
                default -> CLOSED_X;
            };
        } else if (stage == 1) {
            boolean hingeLeft = hinge == DoorHingeSide.LEFT;
            return switch (direction) {
                case NORTH -> hingeLeft ? OPEN_X_LEFT_STAGE_1 : OPEN_X_RIGHT_STAGE_1;
                case SOUTH -> hingeLeft ? OPEN_X_RIGHT_STAGE_1 : OPEN_X_LEFT_STAGE_1;
                case EAST -> hingeLeft ? OPEN_Z_LEFT_STAGE_1 : OPEN_Z_RIGHT_STAGE_1;
                case WEST -> hingeLeft ? OPEN_Z_RIGHT_STAGE_1 : OPEN_Z_LEFT_STAGE_1;
                default -> CLOSED_X;
            };
        } else {
            boolean hingeLeft = hinge == DoorHingeSide.LEFT;
            return switch (direction) {
                case NORTH -> hingeLeft ? OPEN_X_LEFT_STAGE_2 : OPEN_X_RIGHT_STAGE_2;
                case SOUTH -> hingeLeft ? OPEN_X_RIGHT_STAGE_2 : OPEN_X_LEFT_STAGE_2;
                case EAST -> hingeLeft ? OPEN_Z_LEFT_STAGE_2 : OPEN_Z_RIGHT_STAGE_2;
                case WEST -> hingeLeft ? OPEN_Z_RIGHT_STAGE_2 : OPEN_Z_LEFT_STAGE_2;
                default -> CLOSED_X;
            };
        }
    }
}
