package com.axperty.yakisugi.block.custom;

import com.axperty.yakisugi.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.util.RandomSource;
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

    // Lantern Types
    public static final int EMPTY = 0;
    public static final int NORMAL_LANTERN = 1;
    public static final int SOUL_LANTERN = 2;
    public static final int COPPER_LANTERN = 3;
    public static final int EXPOSED_COPPER_LANTERN = 4;
    public static final int WEATHERED_COPPER_LANTERN = 5;
    public static final int OXIDIZED_COPPER_LANTERN = 6;
    public static final int WAXED_COPPER_LANTERN = 7;
    public static final int WAXED_EXPOSED_COPPER_LANTERN = 8;
    public static final int WAXED_WEATHERED_COPPER_LANTERN = 9;
    public static final int WAXED_OXIDIZED_COPPER_LANTERN = 10;
    public static final IntegerProperty LANTERN_TYPE = IntegerProperty.create("lantern_type", EMPTY, WAXED_OXIDIZED_COPPER_LANTERN);

    // Copper lanterns compat, allows all lanterns, not sure if i should just leave one
    private static final Item COPPER_LANTERN_ITEM = itemById("copper_lantern");
    private static final Item EXPOSED_COPPER_LANTERN_ITEM = itemById("exposed_copper_lantern");
    private static final Item WEATHERED_COPPER_LANTERN_ITEM = itemById("weathered_copper_lantern");
    private static final Item OXIDIZED_COPPER_LANTERN_ITEM = itemById("oxidized_copper_lantern");
    private static final Item WAXED_COPPER_LANTERN_ITEM = itemById("waxed_copper_lantern");
    private static final Item WAXED_EXPOSED_COPPER_LANTERN_ITEM = itemById("waxed_exposed_copper_lantern");
    private static final Item WAXED_WEATHERED_COPPER_LANTERN_ITEM = itemById("waxed_weathered_copper_lantern");
    private static final Item WAXED_OXIDIZED_COPPER_LANTERN_ITEM = itemById("waxed_oxidized_copper_lantern");

    private static Item itemById(String path) {
        return BuiltInRegistries.ITEM.getValue(Identifier.withDefaultNamespace(path));
    }

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
        return below.is(BlockRegistry.AGED_STONE_LANTERN.get()) || below.is(BlockRegistry.POLISHED_AGED_STONE_LANTERN.get());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LANTERN_TYPE, EMPTY);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LANTERN_TYPE);
    }

    // Allow to use a lantern inside the old stone lantern
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int currentType = state.getValue(LANTERN_TYPE);

        if (currentType == EMPTY) {
            int newType = EMPTY;
            if (stack.is(Items.LANTERN)) {
                newType = NORMAL_LANTERN;
            } else if (stack.is(Items.SOUL_LANTERN)) {
                newType = SOUL_LANTERN;
            } else if (stack.is(COPPER_LANTERN_ITEM)) {
                newType = COPPER_LANTERN;
            } else if (stack.is(EXPOSED_COPPER_LANTERN_ITEM)) {
                newType = EXPOSED_COPPER_LANTERN;
            } else if (stack.is(WEATHERED_COPPER_LANTERN_ITEM)) {
                newType = WEATHERED_COPPER_LANTERN;
            } else if (stack.is(OXIDIZED_COPPER_LANTERN_ITEM)) {
                newType = OXIDIZED_COPPER_LANTERN;
            } else if (stack.is(WAXED_COPPER_LANTERN_ITEM)) {
                newType = WAXED_COPPER_LANTERN;
            } else if (stack.is(WAXED_EXPOSED_COPPER_LANTERN_ITEM)) {
                newType = WAXED_EXPOSED_COPPER_LANTERN;
            } else if (stack.is(WAXED_WEATHERED_COPPER_LANTERN_ITEM)) {
                newType = WAXED_WEATHERED_COPPER_LANTERN;
            } else if (stack.is(WAXED_OXIDIZED_COPPER_LANTERN_ITEM)) {
                newType = WAXED_OXIDIZED_COPPER_LANTERN;
            }

            if (newType != EMPTY) {
                if (!level.isClientSide()) {
                    level.setBlock(pos, state.setValue(LANTERN_TYPE, newType), 3);
                    level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                }
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
            }
        } else if (stack.isEmpty() && hand == InteractionHand.MAIN_HAND) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(LANTERN_TYPE, EMPTY), 3);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    Block.popResource(level, pos, new ItemStack(itemForType(currentType)));
                }
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static int getLightEmission(BlockState state) {
        return lightForType(state.getValue(LANTERN_TYPE));
    }

    private static int lightForType(int type) {
        return switch (type) {
            case NORMAL_LANTERN -> 15;
            case SOUL_LANTERN -> 10;
            case COPPER_LANTERN, WAXED_COPPER_LANTERN -> 15;
            case EXPOSED_COPPER_LANTERN, WAXED_EXPOSED_COPPER_LANTERN -> 12;
            case WEATHERED_COPPER_LANTERN, WAXED_WEATHERED_COPPER_LANTERN -> 7;
            case OXIDIZED_COPPER_LANTERN, WAXED_OXIDIZED_COPPER_LANTERN -> 3;
            default -> 0;
        };
    }

    private static Item itemForType(int type) {
        return switch (type) {
            case SOUL_LANTERN -> Items.SOUL_LANTERN;
            case COPPER_LANTERN -> COPPER_LANTERN_ITEM;
            case EXPOSED_COPPER_LANTERN -> EXPOSED_COPPER_LANTERN_ITEM;
            case WEATHERED_COPPER_LANTERN -> WEATHERED_COPPER_LANTERN_ITEM;
            case OXIDIZED_COPPER_LANTERN -> OXIDIZED_COPPER_LANTERN_ITEM;
            case WAXED_COPPER_LANTERN -> WAXED_COPPER_LANTERN_ITEM;
            case WAXED_EXPOSED_COPPER_LANTERN -> WAXED_EXPOSED_COPPER_LANTERN_ITEM;
            case WAXED_WEATHERED_COPPER_LANTERN -> WAXED_WEATHERED_COPPER_LANTERN_ITEM;
            case WAXED_OXIDIZED_COPPER_LANTERN -> WAXED_OXIDIZED_COPPER_LANTERN_ITEM;
            default -> Items.LANTERN;
        };
    }
}
