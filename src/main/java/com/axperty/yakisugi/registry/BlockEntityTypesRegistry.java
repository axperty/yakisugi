package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.block.entity.CabinetBlockEntity;
import com.axperty.yakisugi.block.entity.ModHangingSignBlockEntity;
import com.axperty.yakisugi.block.entity.ModSignBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityTypesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Yakisugi.MOD_ID);

    // Slightly/Charred Wall Signs
    public static final Supplier<BlockEntityType<ModSignBlockEntity>> MOD_SIGN = BLOCK_ENTITIES.register("mod_sign",
            () -> new BlockEntityType<>(ModSignBlockEntity::new,
                    BlockRegistry.CHARRED_SIGN.get(), BlockRegistry.CHARRED_WALL_SIGN.get(),
                    BlockRegistry.SLIGHTLY_CHARRED_SIGN.get(), BlockRegistry.SLIGHTLY_CHARRED_WALL_SIGN.get()
            ));

    // Slightly/Charred Hanging Signs
    public static final Supplier<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN = BLOCK_ENTITIES.register("mod_hanging_sign",
            () -> new BlockEntityType<>(ModHangingSignBlockEntity::new,
                    BlockRegistry.CHARRED_HANGING_SIGN.get(), BlockRegistry.CHARRED_WALL_HANGING_SIGN.get(),
                    BlockRegistry.SLIGHTLY_CHARRED_HANGING_SIGN.get(), BlockRegistry.SLIGHTLY_CHARRED_WALL_HANGING_SIGN.get()
            ));

    // Slightly/Charred Cabinets
    public static final Supplier<BlockEntityType<CabinetBlockEntity>> CABINET = BLOCK_ENTITIES.register("cabinet",
            () -> new BlockEntityType<>(CabinetBlockEntity::new,
                    BlockRegistry.CHARRED_CABINET.get(), BlockRegistry.SLIGHTLY_CHARRED_CABINET.get()
            ));
}
