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
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Yakisugi.MOD_ID);

    public static final DeferredItem<Item> CHARRED_BOAT = ITEMS.register("charred_boat",
            () -> new BoatItem(CharredBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CHARRED_CHEST_BOAT = ITEMS.register("charred_chest_boat",
            () -> new BoatItem(CharredChestBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_BOAT = ITEMS.register("slightly_charred_boat",
            () -> new BoatItem(SlightlyCharredBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_CHEST_BOAT = ITEMS.register("slightly_charred_chest_boat",
            () -> new BoatItem(SlightlyCharredChestBoatEntity::new, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CHARRED_SIGN = ITEMS.register("charred_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.CHARRED_SIGN.get(), BlockRegistry.CHARRED_WALL_SIGN.get()));

    public static final DeferredItem<Item> CHARRED_HANGING_SIGN = ITEMS.register("charred_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.CHARRED_HANGING_SIGN.get(), BlockRegistry.CHARRED_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_SIGN = ITEMS.register("slightly_charred_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.SLIGHTLY_CHARRED_SIGN.get(), BlockRegistry.SLIGHTLY_CHARRED_WALL_SIGN.get()));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_HANGING_SIGN = ITEMS.register("slightly_charred_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.SLIGHTLY_CHARRED_HANGING_SIGN.get(), BlockRegistry.SLIGHTLY_CHARRED_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> IRON_SAND = ITEMS.registerSimpleItem("iron_sand");

    public static final DeferredItem<Item> TAMAHAGANE_CHUNK = ITEMS.registerSimpleItem("tamahagane_chunk");

    public static final DeferredItem<Item> KATANA = ITEMS.register("katana",
            () -> new KatanaItem(Tiers.IRON, new Item.Properties()
                    .stacksTo(1)
                    .durability(Tiers.IRON.getUses())
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 3.0F, -2.0F))));

    public static final DeferredItem<Item> SHURIKEN = ITEMS.register("shuriken",
            () -> new ShurikenItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> CHARRED_CABINET = ITEMS.register("charred_cabinet",
            () -> new BlockItem(BlockRegistry.CHARRED_CABINET.get(), new Item.Properties()));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_CABINET = ITEMS.register("slightly_charred_cabinet",
            () -> new BlockItem(BlockRegistry.SLIGHTLY_CHARRED_CABINET.get(), new Item.Properties()));

    public static final DeferredItem<Item> WHEAT_STRAW = ITEMS.registerSimpleItem("wheat_straw");

    public static final DeferredItem<Item> STRAW_HAT = ITEMS.register("straw_hat",
            () -> new StrawHatItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> STRAW_MINO = ITEMS.register("straw_mino",
            () -> new StrawMinoItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> STRAW_BOOTS = ITEMS.register("straw_boots",
            () -> new StrawBootsItem(new Item.Properties().stacksTo(1)));
}
