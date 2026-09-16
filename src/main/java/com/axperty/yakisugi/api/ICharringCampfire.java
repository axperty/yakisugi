package com.axperty.yakisugi.api;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;

public interface ICharringCampfire {
    Object2IntMap<BlockPos> yakisugi$getCharringMap();
}
