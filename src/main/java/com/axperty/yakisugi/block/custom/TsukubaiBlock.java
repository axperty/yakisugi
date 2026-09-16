package com.axperty.yakisugi.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TsukubaiBlock extends Block {
    public static final BooleanProperty WATER = BooleanProperty.create("water");

    // Custom voxel shape for tsukubai block
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);

    // Droplets Coordinates
    // This doesn't exactly work that well, the problem is that when the player moves their camera,
    // the water particles also move along with the camera, causing it to look misaligned with the bamboo
    private static final double DRIP_X = 10.0D / 16.0D;
    private static final double DRIP_Y = 9.0D / 16.0D;
    private static final double DRIP_Z = 7.0D / 16.0D;

    public TsukubaiBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATER, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATER);
    }

    // Allow using with a water bucket
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        boolean filled = state.getValue(WATER);

        if (!filled && stack.is(Items.WATER_BUCKET)) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(WATER, true), 3);
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    stack.shrink(1);
                    Block.popResource(level, pos, new ItemStack(Items.BUCKET));
                }
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        } else if (filled && stack.is(Items.BUCKET)) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(WATER, false), 3);
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    stack.shrink(1);
                    Block.popResource(level, pos, new ItemStack(Items.WATER_BUCKET));
                }
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    // Droplets Particles and Sounds
    // The sound of this doesn't really work, need to check why
    // but droplets work!
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(WATER)) {
            return;
        }

        double x = pos.getX() + DRIP_X;
        double y = pos.getY() + DRIP_Y;
        double z = pos.getZ() + DRIP_Z;

        if (random.nextInt(6) == 0) {
            level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
        }

        if (random.nextInt(120) == 0) {
            // Maybe using POINTED_DRIPSTONE_DRIP_WATER will make it work?
            level.playLocalSound(x, y, z, SoundEvents.POINTED_DRIPSTONE_DRIP_WATER,
                    SoundSource.BLOCKS, 0.3F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.2F, false);
        }
    }
}
