package com.axperty.yakisugi.mixin;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    // Make the campfire unlit when placed, which requires a bit more effort to make new wood types
    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    private void yakisugi$defaultUnlit(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state != null && state.hasProperty(CampfireBlock.LIT)) {
            cir.setReturnValue(state.setValue(CampfireBlock.LIT, false));
        }
    }
}
