package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityTypesRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Yakisugi.MOD_ID);

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, name));
    }

    // Charred Boat
    public static final Supplier<EntityType<CharredBoatEntity>> CHARRED_BOAT = ENTITY_TYPES.register("charred_boat",
            () -> EntityType.Builder.<CharredBoatEntity>of(CharredBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build(key("charred_boat")));

    // Charred Chest Boat
    public static final Supplier<EntityType<CharredChestBoatEntity>> CHARRED_CHEST_BOAT = ENTITY_TYPES.register("charred_chest_boat",
            () -> EntityType.Builder.<CharredChestBoatEntity>of(CharredChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build(key("charred_chest_boat")));

    // Slightly Charred Boat
    public static final Supplier<EntityType<SlightlyCharredBoatEntity>> SLIGHTLY_CHARRED_BOAT = ENTITY_TYPES.register("slightly_charred_boat",
            () -> EntityType.Builder.<SlightlyCharredBoatEntity>of(SlightlyCharredBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build(key("slightly_charred_boat")));

    // Slightly Charred Boat with Chest
    public static final Supplier<EntityType<SlightlyCharredChestBoatEntity>> SLIGHTLY_CHARRED_CHEST_BOAT = ENTITY_TYPES.register("slightly_charred_chest_boat",
            () -> EntityType.Builder.<SlightlyCharredChestBoatEntity>of(SlightlyCharredChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build(key("slightly_charred_chest_boat")));

    // Shuriken
    public static final Supplier<EntityType<ShurikenEntity>> SHURIKEN = ENTITY_TYPES.register("shuriken",
            () -> EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(key("shuriken")));
}
