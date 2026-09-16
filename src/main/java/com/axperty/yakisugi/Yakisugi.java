package com.axperty.yakisugi;

import com.axperty.yakisugi.registry.BlockEntityTypesRegistry;
import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.WoodTypesRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import com.axperty.yakisugi.registry.BlockRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import com.axperty.yakisugi.registry.CreativeTabRegistry;

@Mod(Yakisugi.MOD_ID)
public class Yakisugi {
    public static final String MOD_ID = "yakisugi";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Yakisugi(IEventBus modEventBus) {
        BlockRegistry.BLOCKS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockEntityTypesRegistry.BLOCK_ENTITIES.register(modEventBus);
        EntityTypesRegistry.ENTITY_TYPES.register(modEventBus);
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        WoodTypesRegistry.register();
        LOGGER.info("Yakisugi loaded");
    }
}
