package com.axperty.yakisugi;

import com.axperty.yakisugi.registry.BlockEntityTypesRegistry;
import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.EventsRegistry;
import com.axperty.yakisugi.registry.WoodTypesRegistry;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

import com.axperty.yakisugi.registry.BlockRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import com.axperty.yakisugi.registry.CreativeTabRegistry;

public class Yakisugi implements ModInitializer {
    public static final String MOD_ID = "yakisugi";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        WoodTypesRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();
        BlockEntityTypesRegistry.register();
        EntityTypesRegistry.register();
        CreativeTabRegistry.register();
        EventsRegistry.register();
        LOGGER.info("Yakisugi loaded");
    }
}
