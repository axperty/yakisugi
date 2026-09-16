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
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Yakisugi.MOD_ID);

    // needs an asset id or the armor layer just drops it from the render state and it never shows up worn
    private static Equippable equippableWithAsset(EquipmentSlot slot, String name) {
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, name));
        return Equippable.builder(slot).setAsset(asset).build();
    }

    public static final DeferredItem<Item> CHARRED_BOAT = ITEMS.registerItem("charred_boat",
            properties -> new BoatItem(CharredBoatEntity::new, properties), () -> new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> CHARRED_CHEST_BOAT = ITEMS.registerItem("charred_chest_boat",
            properties -> new BoatItem(CharredChestBoatEntity::new, properties), () -> new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_BOAT = ITEMS.registerItem("slightly_charred_boat",
            properties -> new BoatItem(SlightlyCharredBoatEntity::new, properties), () -> new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_CHEST_BOAT = ITEMS.registerItem("slightly_charred_chest_boat",
            properties -> new BoatItem(SlightlyCharredChestBoatEntity::new, properties), () -> new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> CHARRED_SIGN = ITEMS.registerItem("charred_sign",
            properties -> new SignItem(properties, BlockRegistry.CHARRED_SIGN.get(), BlockRegistry.CHARRED_WALL_SIGN.get(), Direction.UP), () -> new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());

    public static final DeferredItem<Item> CHARRED_HANGING_SIGN = ITEMS.registerItem("charred_hanging_sign",
            properties -> new HangingSignItem(BlockRegistry.CHARRED_HANGING_SIGN.get(), BlockRegistry.CHARRED_WALL_HANGING_SIGN.get(), properties), () -> new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_SIGN = ITEMS.registerItem("slightly_charred_sign",
            properties -> new SignItem(properties, BlockRegistry.SLIGHTLY_CHARRED_SIGN.get(), BlockRegistry.SLIGHTLY_CHARRED_WALL_SIGN.get(), Direction.UP), () -> new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_HANGING_SIGN = ITEMS.registerItem("slightly_charred_hanging_sign",
            properties -> new HangingSignItem(BlockRegistry.SLIGHTLY_CHARRED_HANGING_SIGN.get(), BlockRegistry.SLIGHTLY_CHARRED_WALL_HANGING_SIGN.get(), properties), () -> new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());

    public static final DeferredItem<Item> IRON_SAND = ITEMS.registerSimpleItem("iron_sand");

    public static final DeferredItem<Item> TAMAHAGANE_CHUNK = ITEMS.registerSimpleItem("tamahagane_chunk");

    private static final TagKey<Item> KATANA_REPAIR_MATERIALS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, "katana_repair_materials"));

    public static final DeferredItem<Item> KATANA = ITEMS.registerItem("katana",
            KatanaItem::new, () -> new Item.Properties()
                    .stacksTo(1)
                    .sword(ToolMaterial.IRON, 3.0F, -2.0F)
                    .repairable(KATANA_REPAIR_MATERIALS));

    public static final DeferredItem<Item> SHURIKEN = ITEMS.registerItem("shuriken",
            ShurikenItem::new, () -> new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> CHARRED_CABINET = ITEMS.registerItem("charred_cabinet",
            properties -> new BlockItem(BlockRegistry.CHARRED_CABINET.get(), properties), () -> new Item.Properties().useBlockDescriptionPrefix());

    public static final DeferredItem<Item> SLIGHTLY_CHARRED_CABINET = ITEMS.registerItem("slightly_charred_cabinet",
            properties -> new BlockItem(BlockRegistry.SLIGHTLY_CHARRED_CABINET.get(), properties), () -> new Item.Properties().useBlockDescriptionPrefix());

    public static final DeferredItem<Item> WHEAT_STRAW = ITEMS.registerSimpleItem("wheat_straw");

    public static final DeferredItem<Item> STRAW_HAT = ITEMS.registerItem("straw_hat",
            StrawHatItem::new, () -> new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, equippableWithAsset(EquipmentSlot.HEAD, "straw_hat")));

    public static final DeferredItem<Item> STRAW_MINO = ITEMS.registerItem("straw_mino",
            StrawMinoItem::new, () -> new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, equippableWithAsset(EquipmentSlot.CHEST, "straw_mino")));

    public static final DeferredItem<Item> STRAW_BOOTS = ITEMS.registerItem("straw_boots",
            StrawBootsItem::new, () -> new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, equippableWithAsset(EquipmentSlot.FEET, "straw_boots")));
}
