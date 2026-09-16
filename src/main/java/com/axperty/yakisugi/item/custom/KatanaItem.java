package com.axperty.yakisugi.item.custom;

import com.axperty.yakisugi.registry.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;

public class KatanaItem extends SwordItem {
    private static final float DASH_DAMAGE = 8.0F;
    private static final double DASH_STRENGTH = 1.4;
    private static final int TRAIL_POINTS = 6;

    public KatanaItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (attacker.level() instanceof ServerLevel serverLevel && attacker instanceof ServerPlayer player
                && target.isAlive() && isCriticalHit(player)) {
            Vec3 dir = target.position().subtract(player.position());
            dir = dir.lengthSqr() > 1.0E-4 ? dir.normalize() : player.getLookAngle();

            player.setDeltaMovement(dir.scale(DASH_STRENGTH).add(0, 0.1, 0));
            player.hurtMarked = true;

            spawnWindTrail(serverLevel, player.position().add(0, 1.0, 0), dir);

            target.hurt(player.damageSources().playerAttack(player), DASH_DAMAGE);
        }
        return result;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.TAMAHAGANE_CHUNK.get()) || super.isValidRepairItem(toRepair, repair);
    }

    private boolean isCriticalHit(ServerPlayer player) {
        return player.fallDistance > 0.0F
                && !player.onGround()
                && !player.onClimbable()
                && !player.isInWater()
                && !player.hasEffect(MobEffects.BLINDNESS)
                && !player.isPassenger()
                && !player.isSprinting();
    }

    private void spawnWindTrail(ServerLevel level, Vec3 origin, Vec3 dir) {
        for (int i = 1; i <= TRAIL_POINTS; i++) {
            Vec3 point = origin.add(dir.scale(i * 0.5));
            level.sendParticles(ParticleTypes.GUST, point.x, point.y, point.z, 3, 0.15, 0.15, 0.15, 0.01);
            level.sendParticles(ParticleTypes.CLOUD, point.x, point.y, point.z, 2, 0.12, 0.12, 0.12, 0.01);
        }
    }
}
