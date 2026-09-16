package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.block.custom.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Yakisugi.MOD_ID);

    // Shoji Screen
    public static final DeferredBlock<Block> SHOJI_SCREEN = registerBlock("shoji_screen",
            ShojiScreenBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.BAMBOO_WOOD).noOcclusion());

    // Shoji Bone
    public static final DeferredBlock<Block> SHOJI_BONE = registerBlock("shoji_bone",
            ShojiScreenBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).sound(SoundType.BAMBOO_WOOD).noOcclusion());

    // Shoji Door
    public static final DeferredBlock<Block> SHOJI_DOOR = registerBlock("shoji_door",
            properties -> new ShojiDoorBlock(BlockSetType.BAMBOO, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_DOOR).sound(SoundType.BAMBOO_WOOD));

    // Bamboo Cladding Wall
    public static final DeferredBlock<Block> BAMBOO_CLADDING_WALL = registerBlock("bamboo_cladding_wall",
            BambooCladdingWallBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).noOcclusion());

    // Stripped Bamboo Cladding Wall
    public static final DeferredBlock<Block> STRIPPED_BAMBOO_CLADDING_WALL = registerBlock("stripped_bamboo_cladding_wall",
            StrippedBambooCladdingWallBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).noOcclusion());

    // Tatami Block
    public static final DeferredBlock<Block> TATAMI_BLOCK = registerBlock("tatami_block",
            Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).sound(SoundType.WOOL));

    // Tatami Mat
    public static final DeferredBlock<Block> TATAMI_MAT = registerBlock("tatami_mat",
            CarpetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET).sound(SoundType.WOOL));

    // Paper Lanterns
    public static final DeferredBlock<Block> PAPER_LANTERN = registerBlock("paper_lantern",
            PaperLanternBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).sound(SoundType.WOOL).noOcclusion().lightLevel(state -> 7));

    public static final DeferredBlock<Block> PAPER_LANTERN_CHERRY = registerBlock("paper_lantern_cherry",
            PaperLanternBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).sound(SoundType.WOOL).noOcclusion().lightLevel(state -> 7));

    public static final DeferredBlock<Block> PAPER_LANTERN_MOUNT_FUJI = registerBlock("paper_lantern_mount_fuji",
            PaperLanternBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).sound(SoundType.WOOL).noOcclusion().lightLevel(state -> 7));

    // Aged Stone Block
    public static final DeferredBlock<Block> AGED_STONE = registerBlock("aged_stone",
            Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).explosionResistance(6.0f));

    // Aged Stone Wall
    public static final DeferredBlock<Block> AGED_STONE_WALL = registerBlock("aged_stone_wall",
            WallBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL).explosionResistance(6.0f));

    // Aged Stone Pressure Plate
    public static final DeferredBlock<Block> AGED_STONE_PRESSURE_PLATE = registerBlock("aged_stone_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.STONE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.DEEPSLATE).explosionResistance(6.0f));

    // Aged Stone Button
    public static final DeferredBlock<Block> AGED_STONE_BUTTON = registerBlock("aged_stone_button",
            properties -> new ButtonBlock(BlockSetType.STONE, 20, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON).sound(SoundType.DEEPSLATE).explosionResistance(6.0f));

    // Aged Stone Stairs
    public static final DeferredBlock<Block> AGED_STONE_STAIRS = registerBlock("aged_stone_stairs",
            properties -> new StairBlock(AGED_STONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));

    // Aged Stone Slab
    public static final DeferredBlock<Block> AGED_STONE_SLAB = registerBlock("aged_stone_slab",
            SlabBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));

    // Aged Stone Lantern
    public static final DeferredBlock<Block> AGED_STONE_LANTERN = registerBlock("aged_stone_lantern",
            AgedStoneLanternBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).noOcclusion());

    // Aged Stone Lantern Top
    public static final DeferredBlock<Block> AGED_STONE_LANTERN_TOP = registerBlock("aged_stone_lantern_top",
            AgedStoneLanternTopBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).noOcclusion().lightLevel(AgedStoneLanternTopBlock::getLightEmission));

    // Polished Aged Stone Block
    public static final DeferredBlock<Block> POLISHED_AGED_STONE = registerBlock("polished_aged_stone",
            Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).explosionResistance(6.0f));

    // Polished Aged Stone Wall
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_WALL = registerBlock("polished_aged_stone_wall",
            WallBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL).explosionResistance(6.0f));

    // Polished Aged Stone Pressure Plate
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_PRESSURE_PLATE = registerBlock("polished_aged_stone_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.STONE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.DEEPSLATE).explosionResistance(6.0f));

    // Polished Aged Stone Button
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_BUTTON = registerBlock("polished_aged_stone_button",
            properties -> new ButtonBlock(BlockSetType.STONE, 20, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON).sound(SoundType.DEEPSLATE).explosionResistance(6.0f));

    // Polished Aged Stone Stairs
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_STAIRS = registerBlock("polished_aged_stone_stairs",
            properties -> new StairBlock(POLISHED_AGED_STONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));

    // Polished Aged Stone Slab
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_SLAB = registerBlock("polished_aged_stone_slab",
            SlabBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));

    // Polished Aged Stone Lantern
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_LANTERN = registerBlock("polished_aged_stone_lantern",
            AgedStoneLanternBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).noOcclusion());

    // Polished Aged Stone Lantern Top
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_LANTERN_TOP = registerBlock("polished_aged_stone_lantern_top",
            AgedStoneLanternTopBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).noOcclusion().lightLevel(AgedStoneLanternTopBlock::getLightEmission));

    // Aged Stone Tsukubai
    public static final DeferredBlock<Block> AGED_STONE_TSUKUBAI = registerBlock("aged_stone_tsukubai",
            TsukubaiBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).explosionResistance(6.0f).noOcclusion());

    // Polished Aged Stone Tsukubai
    public static final DeferredBlock<Block> POLISHED_AGED_STONE_TSUKUBAI = registerBlock("polished_aged_stone_tsukubai",
            TsukubaiBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).explosionResistance(6.0f).noOcclusion());

    // Slightly Charred Planks
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_PLANKS = registerBlock("slightly_charred_planks",
            Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .sound(SoundType.WOOD)
                    .explosionResistance(6.0f));

    // Slightly Charred Stairs
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_STAIRS = registerBlock("slightly_charred_stairs",
            properties -> new StairBlock(SLIGHTLY_CHARRED_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).explosionResistance(6.0f));

    // Slightly Charred Slab
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_SLAB = registerBlock("slightly_charred_slab",
            SlabBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).explosionResistance(6.0f));

    // Slightly Charred Fence
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_FENCE = registerBlock("slightly_charred_fence",
            FenceBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).explosionResistance(6.0f));

    // Slightly Charred Fence Gate
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_FENCE_GATE = registerBlock("slightly_charred_fence_gate",
            properties -> new FenceGateBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).explosionResistance(6.0f));

    // Slightly Charred Pressure Plate
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_PRESSURE_PLATE = registerBlock("slightly_charred_pressure_plate",
            properties -> new PressurePlateBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_SET_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).explosionResistance(6.0f));

    // Slightly Charred Button
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_BUTTON = registerBlock("slightly_charred_button",
            properties -> new ButtonBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_SET_TYPE, 30, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).explosionResistance(6.0f));

    // Slightly Charred Signs
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_SIGN = BLOCKS.registerBlock("slightly_charred_sign",
            properties -> new CharredStandingSignBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN));

    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_WALL_SIGN = BLOCKS.registerBlock("slightly_charred_wall_sign",
            properties -> new CharredWallSignBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN));

    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_HANGING_SIGN = BLOCKS.registerBlock("slightly_charred_hanging_sign",
            properties -> new CharredCeilingHangingSignBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN));

    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_WALL_HANGING_SIGN = BLOCKS.registerBlock("slightly_charred_wall_hanging_sign",
            properties -> new CharredWallHangingSignBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN));

    // Charred Planks
    public static final DeferredBlock<Block> CHARRED_PLANKS = registerBlock("charred_planks",
            Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .sound(SoundType.WOOD)
                    .explosionResistance(6.0f));

    // Charred Stairs
    public static final DeferredBlock<Block> CHARRED_STAIRS = registerBlock("charred_stairs",
            properties -> new StairBlock(CHARRED_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).explosionResistance(6.0f));

    // Charred Slab
    public static final DeferredBlock<Block> CHARRED_SLAB = registerBlock("charred_slab",
            SlabBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).explosionResistance(6.0f));

    // Charred Fence
    public static final DeferredBlock<Block> CHARRED_FENCE = registerBlock("charred_fence",
            FenceBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).explosionResistance(6.0f));

    // Charred Fence Gate
    public static final DeferredBlock<Block> CHARRED_FENCE_GATE = registerBlock("charred_fence_gate",
            properties -> new FenceGateBlock(WoodTypesRegistry.CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).explosionResistance(6.0f));

    // Charred Pressure Plate
    public static final DeferredBlock<Block> CHARRED_PRESSURE_PLATE = registerBlock("charred_pressure_plate",
            properties -> new PressurePlateBlock(WoodTypesRegistry.CHARRED_SET_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).explosionResistance(6.0f));

    // Charred Button
    public static final DeferredBlock<Block> CHARRED_BUTTON = registerBlock("charred_button",
            properties -> new ButtonBlock(WoodTypesRegistry.CHARRED_SET_TYPE, 30, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).explosionResistance(6.0f));

    // Charred Signs
    public static final DeferredBlock<Block> CHARRED_SIGN = BLOCKS.registerBlock("charred_sign",
            properties -> new CharredStandingSignBlock(WoodTypesRegistry.CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN));

    public static final DeferredBlock<Block> CHARRED_WALL_SIGN = BLOCKS.registerBlock("charred_wall_sign",
            properties -> new CharredWallSignBlock(WoodTypesRegistry.CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN));

    public static final DeferredBlock<Block> CHARRED_HANGING_SIGN = BLOCKS.registerBlock("charred_hanging_sign",
            properties -> new CharredCeilingHangingSignBlock(WoodTypesRegistry.CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN));

    public static final DeferredBlock<Block> CHARRED_WALL_HANGING_SIGN = BLOCKS.registerBlock("charred_wall_hanging_sign",
            properties -> new CharredWallHangingSignBlock(WoodTypesRegistry.CHARRED_WOOD_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN));

    public static final Supplier<Block> CHARRED_CABINET = BLOCKS.registerBlock("charred_cabinet",
            CabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL).explosionResistance(6.0f));

    public static final Supplier<Block> SLIGHTLY_CHARRED_CABINET = BLOCKS.registerBlock("slightly_charred_cabinet",
            CabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL).explosionResistance(6.0f));

    // Charred Trapdoor
    public static final DeferredBlock<Block> CHARRED_TRAPDOOR = registerBlock("charred_trapdoor",
            properties -> new TrapDoorBlock(WoodTypesRegistry.CHARRED_SET_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).explosionResistance(6.0f));

    // Slightly Charred Trapdoor
    public static final DeferredBlock<Block> SLIGHTLY_CHARRED_TRAPDOOR = registerBlock("slightly_charred_trapdoor",
            properties -> new TrapDoorBlock(WoodTypesRegistry.SLIGHTLY_CHARRED_SET_TYPE, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).explosionResistance(6.0f));

//    // Floor Bed
//    public static final DeferredBlock<Block> FLOOR_BED = registerBlock("floor_bed",
//            () -> new FloorBedBlock(net.minecraft.world.item.DyeColor.BROWN, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_BED).noOcclusion()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, factory, properties);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        // BlockItem doesn't inherit the block's translation key anymore, so it'll show the raw
        // item.yakisugi.xxx key in tooltips unless we point it back at block.yakisugi.xxx ourselves
        ItemRegistry.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties), () -> new Item.Properties().useBlockDescriptionPrefix());
    }
}
