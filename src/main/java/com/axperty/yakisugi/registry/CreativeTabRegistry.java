package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Yakisugi.MOD_ID);

    public static final Supplier<CreativeModeTab> YAKISUGI_TAB = CREATIVE_MODE_TABS.register("yakisugi_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(BlockRegistry.CHARRED_PLANKS.get()))
                    .title(Component.translatable("itemGroup.yakisugi"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.KATANA.get());
                        output.accept(ItemRegistry.SHURIKEN.get());
                        output.accept(ItemRegistry.IRON_SAND.get());
                        output.accept(ItemRegistry.TAMAHAGANE_CHUNK.get());
                        // output.accept(BlockRegistry.FLOOR_BED.get());
                        output.accept(ItemRegistry.WHEAT_HAT.get());
                        output.accept(ItemRegistry.MINO.get());
                        output.accept(ItemRegistry.FUKA_GUTSU.get());
                        output.accept(ItemRegistry.WHEAT_STRAW.get());

                        output.accept(BlockRegistry.SHOJI_SCREEN.get());
                        output.accept(BlockRegistry.SHOJI_DOOR.get());
                        output.accept(BlockRegistry.SHOJI_BONE.get());
                        output.accept(BlockRegistry.BAMBOO_CLADDING_WALL.get());
                        output.accept(BlockRegistry.STRIPPED_BAMBOO_CLADDING_WALL.get());
                        output.accept(BlockRegistry.TATAMI_BLOCK.get());
                        output.accept(BlockRegistry.TATAMI_MAT.get());

                        output.accept(BlockRegistry.PAPER_LANTERN.get());
                        output.accept(BlockRegistry.PAPER_LANTERN_CHERRY.get());
                        output.accept(BlockRegistry.PAPER_LANTERN_MOUNT_FUJI.get());

                        output.accept(BlockRegistry.AGED_STONE.get());
                        output.accept(BlockRegistry.AGED_STONE_STAIRS.get());
                        output.accept(BlockRegistry.AGED_STONE_SLAB.get());
                        output.accept(BlockRegistry.AGED_STONE_WALL.get());
                        output.accept(BlockRegistry.AGED_STONE_PRESSURE_PLATE.get());
                        output.accept(BlockRegistry.AGED_STONE_BUTTON.get());
                        output.accept(BlockRegistry.AGED_STONE_LANTERN.get());
                        output.accept(BlockRegistry.AGED_STONE_LANTERN_TOP.get());
                        output.accept(BlockRegistry.AGED_STONE_TSUKUBAI.get());

                        output.accept(BlockRegistry.POLISHED_AGED_STONE.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_STAIRS.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_SLAB.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_WALL.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_PRESSURE_PLATE.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_BUTTON.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_LANTERN.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_LANTERN_TOP.get());
                        output.accept(BlockRegistry.POLISHED_AGED_STONE_TSUKUBAI.get());

                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_PLANKS.get());
                        if (ModList.get().isLoaded("farmersdelight") || ModList.get().isLoaded("storagedelight")) {
                            output.accept(BlockRegistry.SLIGHTLY_CHARRED_CABINET.get());
                        }
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_STAIRS.get());
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_SLAB.get());
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_FENCE.get());
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_FENCE_GATE.get());
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_PRESSURE_PLATE.get());
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_BUTTON.get());
                        output.accept(BlockRegistry.SLIGHTLY_CHARRED_TRAPDOOR.get());

                        output.accept(BlockRegistry.CHARRED_PLANKS.get());
                        if (ModList.get().isLoaded("farmersdelight") || ModList.get().isLoaded("storagedelight")) {
                            output.accept(BlockRegistry.CHARRED_CABINET.get());
                        }
                        output.accept(BlockRegistry.CHARRED_STAIRS.get());
                        output.accept(BlockRegistry.CHARRED_SLAB.get());
                        output.accept(BlockRegistry.CHARRED_FENCE.get());
                        output.accept(BlockRegistry.CHARRED_FENCE_GATE.get());
                        output.accept(BlockRegistry.CHARRED_PRESSURE_PLATE.get());
                        output.accept(BlockRegistry.CHARRED_BUTTON.get());
                        output.accept(BlockRegistry.CHARRED_TRAPDOOR.get());

                        output.accept(ItemRegistry.CHARRED_SIGN.get());
                        output.accept(ItemRegistry.CHARRED_HANGING_SIGN.get());
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_SIGN.get());
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_HANGING_SIGN.get());

                        output.accept(ItemRegistry.CHARRED_BOAT.get());
                        output.accept(ItemRegistry.CHARRED_CHEST_BOAT.get());
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_BOAT.get());
                        output.accept(ItemRegistry.SLIGHTLY_CHARRED_CHEST_BOAT.get());
                    })
                    .build());
}
