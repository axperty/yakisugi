package com.axperty.yakisugi.entity;

import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ShurikenEntity extends ThrowableItemProjectile {
    private static final float DAMAGE = 3.0F;
    private static final int DURATION_TICKS = 20;
    private static final int AMPLIFIER = 1;

    public ShurikenEntity(EntityType<? extends ShurikenEntity> type, Level level) {
        super(type, level);
    }

    public ShurikenEntity(Level level, LivingEntity shooter) {
        super(EntityTypesRegistry.SHURIKEN.get(), shooter, level);
    }

    public ShurikenEntity(Level level, double x, double y, double z) {
        super(EntityTypesRegistry.SHURIKEN.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SHURIKEN.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        // only living stuff takes damage, don't wanna break boats/item frames
        if (!(target instanceof LivingEntity living)) {
            dropAsItem();
            return;
        }

        if (!this.level().isClientSide) {
            living.hurt(this.level().damageSources().thrown(this, this.getOwner()), DAMAGE);
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, DURATION_TICKS, AMPLIFIER));
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        dropAsItem();
    }

    private void dropAsItem() {
        if (this.level().isClientSide) {
            return;
        }
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem().copy());
        itemEntity.setDefaultPickUpDelay();
        this.level().addFreshEntity(itemEntity);
        this.discard();
    }
}
