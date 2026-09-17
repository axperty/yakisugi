package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.entity.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityTypesRegistry {
    // Charred Boat
    public static final EntityType<CharredBoatEntity> CHARRED_BOAT = register("charred_boat",
            EntityType.Builder.<CharredBoatEntity>of(CharredBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build("charred_boat"));

    // Charred Chest Boat
    public static final EntityType<CharredChestBoatEntity> CHARRED_CHEST_BOAT = register("charred_chest_boat",
            EntityType.Builder.<CharredChestBoatEntity>of(CharredChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build("charred_chest_boat"));

    // Slightly Charred Boat
    public static final EntityType<SlightlyCharredBoatEntity> SLIGHTLY_CHARRED_BOAT = register("slightly_charred_boat",
            EntityType.Builder.<SlightlyCharredBoatEntity>of(SlightlyCharredBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build("slightly_charred_boat"));

    // Slightly Charred Boat with Chest
    public static final EntityType<SlightlyCharredChestBoatEntity> SLIGHTLY_CHARRED_CHEST_BOAT = register("slightly_charred_chest_boat",
            EntityType.Builder.<SlightlyCharredChestBoatEntity>of(SlightlyCharredChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build("slightly_charred_chest_boat"));

    // Shuriken
    public static final EntityType<ShurikenEntity> SHURIKEN = register("shuriken",
            EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("shuriken"));

    private static <T extends EntityType<?>> T register(String name, T type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, name), type);
    }

    public static void register() {}
}
