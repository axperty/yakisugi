package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.block.entity.CabinetBlockEntity;
import com.axperty.yakisugi.block.entity.ModHangingSignBlockEntity;
import com.axperty.yakisugi.block.entity.ModSignBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypesRegistry {
    // Slightly/Charred Wall Signs
    public static final BlockEntityType<ModSignBlockEntity> MOD_SIGN = register("mod_sign",
            BlockEntityType.Builder.of(ModSignBlockEntity::new,
                    BlockRegistry.CHARRED_SIGN, BlockRegistry.CHARRED_WALL_SIGN,
                    BlockRegistry.SLIGHTLY_CHARRED_SIGN, BlockRegistry.SLIGHTLY_CHARRED_WALL_SIGN
            ).build(null));

    // Slightly/Charred Hanging Signs
    public static final BlockEntityType<ModHangingSignBlockEntity> MOD_HANGING_SIGN = register("mod_hanging_sign",
            BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                    BlockRegistry.CHARRED_HANGING_SIGN, BlockRegistry.CHARRED_WALL_HANGING_SIGN,
                    BlockRegistry.SLIGHTLY_CHARRED_HANGING_SIGN, BlockRegistry.SLIGHTLY_CHARRED_WALL_HANGING_SIGN
            ).build(null));

    // Slightly/Charred Cabinets
    public static final BlockEntityType<CabinetBlockEntity> CABINET = register("cabinet",
            BlockEntityType.Builder.of(CabinetBlockEntity::new,
                            BlockRegistry.CHARRED_CABINET, BlockRegistry.SLIGHTLY_CHARRED_CABINET)
                    .build(null));

    private static <T extends BlockEntityType<?>> T register(String name, T type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, name), type);
    }

    public static void register() {}
}
