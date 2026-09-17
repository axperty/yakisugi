package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabRegistry {
    public static final CreativeModeTab YAKISUGI_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "yakisugi_tab"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(BlockRegistry.CHARRED_PLANKS))
                    .title(Component.translatable("itemGroup.yakisugi"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.KATANA);
                        output.accept(ItemRegistry.SHURIKEN);
                        output.accept(ItemRegistry.IRON_SAND);
                        output.accept(ItemRegistry.TAMAHAGANE_CHUNK);
//                        output.accept(BlockRegistry.FLOOR_BED);
                        output.accept(ItemRegistry.WHEAT_STRAW);
                        output.accept(ItemRegistry.STRAW_HAT);
                        output.accept(ItemRegistry.STRAW_MINO);
                        output.accept(ItemRegistry.STRAW_BOOTS);

                        output.accept(BlockRegistry.SHOJI_SCREEN);
                        output.accept(BlockRegistry.SHOJI_DOOR);
                        output.accept(BlockRegistry.SHOJI_BONE);
                        output.accept(BlockRegistry.BAMBOO_CLADDING_WALL);
                        output.accept(BlockRegistry.STRIPPED_BAMBOO_CLADDING_WALL);
                        output.accept(BlockRegistry.TATAMI_BLOCK);
                        output.accept(BlockRegistry.TATAMI_MAT);

                        output.accept(BlockRegistry.PAPER_LANTERN);
                        output.accept(BlockRegistry.PAPER_LANTERN_CHERRY);
                        output.accept(BlockRegistry.PAPER_LANTERN_MOUNT_FUJI);

                        output.accept(BlockRegistry.AGED_STONE);
                        output.accept(BlockRegistry.AGED_STONE_STAIRS);
                        output.accept(BlockRegistry.AGED_STONE_SLAB);
                        output.accept(BlockRegistry.AGED_STONE_WALL);
                        output.accept(BlockRegistry.AGED_STONE_PRESSURE_PLATE);
                        output.accept(BlockRegistry.AGED_STONE_BUTTON);
                        output.accept(BlockRegistry.AGED_STONE_LANTERN);
                        output.accept(BlockRegistry.AGED_STONE_LANTERN_TOP);
                        output.accept(BlockRegistry.AGED_STONE_TSUKUBAI);

                        output.accept(BlockRegistry.POLISHED_AGED_STONE);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_STAIRS);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_SLAB);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_WALL);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_PRESSURE_PLATE);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_BUTTON);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_LANTERN);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_LANTERN_TOP);
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_TSUKUBAI);

                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_PLANKS);
                        if (FabricLoader.getInstance().isModLoaded("farmersdelight") || FabricLoader.getInstance().isModLoaded("storagedelight")) {
                            output.accept(BlockRegistry.SLIGHTLY_CHARRED_CABINET);
                        }
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_STAIRS);
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_SLAB);
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_FENCE);
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_FENCE_GATE);
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_PRESSURE_PLATE);
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_BUTTON);
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_TRAPDOOR);

                        output.accept(BlockRegistry.CHARRED_PLANKS);
                        if (FabricLoader.getInstance().isModLoaded("farmersdelight") || FabricLoader.getInstance().isModLoaded("storagedelight")) {
                            output.accept(BlockRegistry.CHARRED_CABINET);
                        }
                        output.accept(BlockRegistry.CHARRED_STAIRS);
                        output.accept(BlockRegistry.CHARRED_SLAB);
                        output.accept(BlockRegistry.CHARRED_FENCE);
                        output.accept(BlockRegistry.CHARRED_FENCE_GATE);
                        output.accept(BlockRegistry.CHARRED_PRESSURE_PLATE);
                        output.accept(BlockRegistry.CHARRED_BUTTON);
                        output.accept(BlockRegistry.CHARRED_TRAPDOOR);

                        output.accept(ItemRegistry.CHARRED_SIGN);
                        output.accept(ItemRegistry.CHARRED_HANGING_SIGN);
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_SIGN);
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_HANGING_SIGN);

                        output.accept(ItemRegistry.CHARRED_BOAT);
                        output.accept(ItemRegistry.CHARRED_CHEST_BOAT);
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_BOAT);
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_CHEST_BOAT);
                    })
                    .build());

    public static void register() {}
}
