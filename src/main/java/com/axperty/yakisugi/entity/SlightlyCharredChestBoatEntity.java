package com.axperty.yakisugi.entity;

import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.Level;

public class SlightlyCharredChestBoatEntity extends ChestBoat {
    public SlightlyCharredChestBoatEntity(EntityType<? extends ChestBoat> type, Level level) {
        super(type, level, ItemRegistry.SLIGHTLY_CHARRED_CHEST_BOAT);
    }

    public SlightlyCharredChestBoatEntity(Level level, double x, double y, double z) {
        this(EntityTypesRegistry.SLIGHTLY_CHARRED_CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
    }
}
