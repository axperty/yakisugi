package com.axperty.yakisugi.entity;

import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.Level;

public class CharredChestBoatEntity extends ChestBoat {
    public CharredChestBoatEntity(EntityType<? extends ChestBoat> type, Level level) {
        super(type, level, ItemRegistry.CHARRED_CHEST_BOAT);
    }

    public CharredChestBoatEntity(Level level, double x, double y, double z) {
        this(EntityTypesRegistry.CHARRED_CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
    }
}
