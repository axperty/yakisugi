package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodTypesRegistry {
    public static final BlockSetType CHARRED_SET_TYPE = BlockSetType.register(new BlockSetType(Yakisugi.MOD_ID + ":charred"));
    public static final WoodType CHARRED_WOOD_TYPE = WoodType.register(new WoodType(Yakisugi.MOD_ID + ":charred", CHARRED_SET_TYPE));
    
    public static final BlockSetType SLIGHTLY_CHARRED_SET_TYPE = BlockSetType.register(new BlockSetType(Yakisugi.MOD_ID + ":slightly_charred"));
    public static final WoodType SLIGHTLY_CHARRED_WOOD_TYPE = WoodType.register(new WoodType(Yakisugi.MOD_ID + ":slightly_charred", SLIGHTLY_CHARRED_SET_TYPE));

    public static void register() {}
}
