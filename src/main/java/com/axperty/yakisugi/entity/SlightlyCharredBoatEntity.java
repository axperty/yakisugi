package com.axperty.yakisugi.entity;

import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SlightlyCharredBoatEntity extends Boat {
    public SlightlyCharredBoatEntity(EntityType<? extends Boat> type, Level level) {
        super(type, level);
    }

    public SlightlyCharredBoatEntity(Level level, double x, double y, double z) {
        this(EntityTypesRegistry.SLIGHTLY_CHARRED_BOAT.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    public Item getDropItem() {
        return ItemRegistry.SLIGHTLY_CHARRED_BOAT.get();
    }
}
