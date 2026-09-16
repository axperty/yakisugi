package com.axperty.yakisugi.block.entity;

import com.axperty.yakisugi.block.custom.CabinetBlock;
import com.axperty.yakisugi.registry.BlockEntityTypesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.List;

// Taken from Storage Delight which also takes it from the Barrel block
public class CabinetBlockEntity extends RandomizableContainerBlockEntity
{
    private static final Component DEFAULT_NAME = Component.translatable("container.yakisugi.cabinet");
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public CabinetBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypesRegistry.CABINET.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
                CabinetBlockEntity.this.playSound(p_155064_, SoundEvents.BARREL_OPEN);
                CabinetBlockEntity.this.updateBlockState(p_155064_, true);
            }

            protected void onClose(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
                CabinetBlockEntity.this.playSound(p_155074_, SoundEvents.BARREL_CLOSE);
                CabinetBlockEntity.this.updateBlockState(p_155074_, false);
            }

            protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
            }

            public boolean isOwnContainer(Player p_155060_) {
                if (p_155060_.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)p_155060_.containerMenu).getContainer();
                    return container == CabinetBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.items);
        }

    }

    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.items);
        }

    }

    public int getContainerSize() {
        return 27;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return ChestMenu.threeRows(id, player, this);
    }

    public void startOpen(ContainerUser user) {
        if (!this.remove && !user.getLivingEntity().isSpectator()) {
            this.openersCounter.incrementOpeners(user.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), user.getContainerInteractionRange());
        }

    }

    public void stopOpen(ContainerUser user) {
        if (!this.remove && !user.getLivingEntity().isSpectator()) {
            this.openersCounter.decrementOpeners(user.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public List<ContainerUser> getEntitiesWithContainerOpen() {
        return this.openersCounter.getEntitiesWithContainerOpen(this.getLevel(), this.getBlockPos());
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    private void updateBlockState(BlockState state, boolean isOpen) {
        this.level.setBlock(this.getBlockPos(), (BlockState)state.setValue(CabinetBlock.OPEN, isOpen), 3);
    }

    private void playSound(BlockState state, SoundEvent event) {
        Vec3i direction = ((Direction)state.getValue(CabinetBlock.FACING)).getUnitVec3i();
        double x = (double)this.worldPosition.getX() + (double)0.5F + (double)direction.getX() / (double)2.0F;
        double y = (double)this.worldPosition.getY() + (double)0.5F + (double)direction.getY() / (double)2.0F;
        double z = (double)this.worldPosition.getZ() + (double)0.5F + (double)direction.getZ() / (double)2.0F;
        this.level.playSound((Entity)null, x, y, z, event, SoundSource.BLOCKS, 0.5F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
    }
}
