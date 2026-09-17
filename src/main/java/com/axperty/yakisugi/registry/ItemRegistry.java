package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.entity.CharredBoatEntity;
import com.axperty.yakisugi.entity.CharredChestBoatEntity;
import com.axperty.yakisugi.entity.SlightlyCharredBoatEntity;
import com.axperty.yakisugi.entity.SlightlyCharredChestBoatEntity;
import com.axperty.yakisugi.item.BoatItem;
import com.axperty.yakisugi.item.custom.StrawBootsItem;
import com.axperty.yakisugi.item.custom.KatanaItem;
import com.axperty.yakisugi.item.custom.StrawMinoItem;
import com.axperty.yakisugi.item.custom.ShurikenItem;
import com.axperty.yakisugi.item.custom.StrawHatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public class ItemRegistry {
    public static final Item CHARRED_BOAT = register("charred_boat",
            new BoatItem(CharredBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final Item CHARRED_CHEST_BOAT = register("charred_chest_boat",
            new BoatItem(CharredChestBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final Item SLIGHTLY_CHARRED_BOAT = register("slightly_charred_boat",
            new BoatItem(SlightlyCharredBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final Item SLIGHTLY_CHARRED_CHEST_BOAT = register("slightly_charred_chest_boat",
            new BoatItem(SlightlyCharredChestBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final Item CHARRED_SIGN = register("charred_sign",
            new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.CHARRED_SIGN, BlockRegistry.CHARRED_WALL_SIGN));

    public static final Item CHARRED_HANGING_SIGN = register("charred_hanging_sign",
            new HangingSignItem(BlockRegistry.CHARRED_HANGING_SIGN, BlockRegistry.CHARRED_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));

    public static final Item SLIGHTLY_CHARRED_SIGN = register("slightly_charred_sign",
            new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.SLIGHTLY_CHARRED_SIGN, BlockRegistry.SLIGHTLY_CHARRED_WALL_SIGN));

    public static final Item SLIGHTLY_CHARRED_HANGING_SIGN = register("slightly_charred_hanging_sign",
            new HangingSignItem(BlockRegistry.SLIGHTLY_CHARRED_HANGING_SIGN, BlockRegistry.SLIGHTLY_CHARRED_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));

    public static final Item IRON_SAND = register("iron_sand", new Item(new Item.Properties()));

    public static final Item TAMAHAGANE_CHUNK = register("tamahagane_chunk", new Item(new Item.Properties()));

    public static final Item KATANA = register("katana",
            new KatanaItem(Tiers.IRON, new Item.Properties()
                    .stacksTo(1)
                    .durability(Tiers.IRON.getUses())
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.0F))));

    public static final Item SHURIKEN = register("shuriken",
            new ShurikenItem(new Item.Properties().stacksTo(16)));

    public static final Item CHARRED_CABINET = register("charred_cabinet",
            new BlockItem(BlockRegistry.CHARRED_CABINET, new Item.Properties()));

    public static final Item SLIGHTLY_CHARRED_CABINET = register("slightly_charred_cabinet",
            new BlockItem(BlockRegistry.SLIGHTLY_CHARRED_CABINET, new Item.Properties()));

    public static final Item WHEAT_STRAW = register("wheat_straw", new Item(new Item.Properties()));

    public static final Item STRAW_HAT = register("straw_hat",
            new StrawHatItem(new Item.Properties().stacksTo(1)));

    public static final Item STRAW_MINO = register("straw_mino",
            new StrawMinoItem(new Item.Properties().stacksTo(1)));

    public static final Item STRAW_BOOTS = register("straw_boots",
            new StrawBootsItem(new Item.Properties().stacksTo(1)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, name), item);
    }

    public static void register() {}
}
