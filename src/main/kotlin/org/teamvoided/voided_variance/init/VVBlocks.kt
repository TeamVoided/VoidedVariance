package org.teamvoided.voided_variance.init

import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings.copy
import net.minecraft.block.Blocks.luminanceOf
import net.minecraft.registry.Registries
import net.minecraft.sound.BlockSoundGroup
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.block.*
import org.teamvoided.voided_variance.utils.datagen.*
import org.teamvoided.voided_variance.utils.register

@Suppress("unused")
object VVBlocks {
    val BLOCKS = mutableListOf<Block>()

    private fun deepslate(): AbstractBlock.Settings =
        AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE)

    val obsidian = copy(Blocks.OBSIDIAN).block()
    val cryingObsidian = copy(Blocks.CRYING_OBSIDIAN).block()

    // Brick fence
    val BRICK_FENCE = register("brick_fence", FenceBlock(copy(Blocks.BRICKS))).pickaxe()

    val REDSTONE_LANTERN = register(
        "redstone_lantern", RedstoneLanternBlock(copy(Blocks.LANTERN).luminance(luminanceOf(8)))
    ).pickaxe().cutout()

    // Infested blocks
    val INFESTED_MOSSY_COBBLESTONE = register(
        "infested_mossy_cobblestone",
        InfestedBlock(Blocks.MOSSY_COBBLESTONE, AbstractBlock.Settings.create().mapColor(MapColor.CLAY))
    ).pickaxe()
    val INFESTED_COBBLED_DEEPSLATE =
        register("infested_cobbled_deepslate", InfestedBlock(Blocks.COBBLED_DEEPSLATE, deepslate())).pickaxe()
    val INFESTED_DEEPSLATE_BRICKS =
        register("infested_deepslate_bricks", InfestedBlock(Blocks.DEEPSLATE_BRICKS, deepslate())).pickaxe()
    val INFESTED_CRACKED_DEEPSLATE_BRICKS = register(
        "infested_cracked_deepslate_bricks", InfestedBlock(Blocks.CRACKED_DEEPSLATE_BRICKS, deepslate())
    ).pickaxe()
    val INFESTED_DEEPSLATE_TILES =
        register("infested_deepslate_tiles", InfestedBlock(Blocks.DEEPSLATE_TILES, deepslate())).pickaxe()
    val INFESTED_CRACKED_DEEPSLATE_TILES = register(
        "infested_cracked_deepslate_tiles", InfestedBlock(Blocks.CRACKED_DEEPSLATE_TILES, deepslate())
    ).pickaxe()
    val INFESTED_POLISHED_DEEPSLATE =
        register("infested_polished_deepslate", InfestedBlock(Blocks.POLISHED_DEEPSLATE, deepslate())).pickaxe()


    // Stairs
    val SMOOTH_STONE_STAIR = register("smooth_stone_stairs", Blocks.SMOOTH_STONE.toStairs()).pickaxe()
    val CRACKED_STONE_BRICKS_STAIR =
        register("cracked_stone_bricks_stairs", Blocks.CRACKED_STONE_BRICKS.toStairs()).pickaxe()
    val CRACKED_DEEPSLATE_TILES_STAIR =
        register("cracked_deepslate_tiles_stairs", Blocks.CRACKED_DEEPSLATE_TILES.toStairs()).pickaxe()
    val CALCITE_STAIR = register("calcite_stairs", Blocks.CALCITE.toStairs()).pickaxe()
    val DRIPSTONE_STAIR = register("dripstone_stairs", Blocks.DRIPSTONE_BLOCK.toStairs()).pickaxe()
    val CRACKED_DEEPSLATE_BRICKS_STAIR =
        register("cracked_deepslate_bricks_stairs", Blocks.CRACKED_DEEPSLATE_BRICKS.toStairs()).pickaxe()
    val PACKED_MUD_STAIR = register("packed_mud_stairs", Blocks.PACKED_MUD.toStairs()).pickaxe()
    val CUT_SANDSTONE_STAIR = register("cut_sandstone_stairs", Blocks.CUT_SANDSTONE.toStairs()).pickaxe()
    val CUT_RED_SANDSTONE_STAIR = register("cut_red_sandstone_stairs", Blocks.CUT_RED_SANDSTONE.toStairs()).pickaxe()
    val CRACKED_NETHER_BRICKS_STAIR =
        register("cracked_nether_bricks_stairs", Blocks.CRACKED_NETHER_BRICKS.toStairs()).pickaxe()
    val SMOOTH_BASALT_STAIR = register("smooth_basalt_stairs", Blocks.SMOOTH_BASALT.toStairs()).pickaxe()
    val CRACKED_POLISHED_BLACKSTONE_BRICKS_STAIR = register(
        "cracked_polished_blackstone_bricks_stairs", Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.toStairs()
    ).pickaxe()
    val OBSIDIAN_STAIR =
        register("obsidian_stairs", VStairsBlock(Blocks.OBSIDIAN, obsidian)).stairs().pickaxe().needsDiamond()
    val CRYING_OBSIDIAN_STAIRS =
        register("crying_obsidian_stairs", CryingStairsBlock(Blocks.CRYING_OBSIDIAN, cryingObsidian))
            .stairs().pickaxe().needsDiamond()
    val END_STONE_STAIR = register("end_stone_stairs", Blocks.END_STONE.toStairs()).pickaxe()
    val QUARTZ_BRICKS_STAIR = register("quartz_bricks_stairs", Blocks.QUARTZ_BRICKS.toStairs()).pickaxe()

    val SNOW_STAIR = register("snow_stairs", Blocks.SNOW_BLOCK.toStairs()).shovel()

    // Slabs
    val CRACKED_STONE_BRICKS_SLAB =
        register("cracked_stone_bricks_slab", Blocks.CRACKED_STONE_BRICKS.toSlab()).pickaxe()
    val CALCITE_SLAB = register("calcite_slab", Blocks.CALCITE.toSlab()).pickaxe()
    val DRIPSTONE_SLAB = register("dripstone_slab", Blocks.DRIPSTONE_BLOCK.toSlab()).pickaxe()
    val CRACKED_DEEPSLATE_BRICKS_SLAB =
        register("cracked_deepslate_bricks_slab", Blocks.CRACKED_DEEPSLATE_BRICKS.toSlab()).pickaxe()
    val CRACKED_DEEPSLATE_TILES_SLAB =
        register("cracked_deepslate_tiles_slab", Blocks.CRACKED_DEEPSLATE_TILES.toSlab()).pickaxe()
    val PACKED_MUD_SLAB = register("packed_mud_slab", Blocks.PACKED_MUD.toSlab()).pickaxe()
    val CRACKED_NETHER_BRICKS_SLAB =
        register("cracked_nether_bricks_slab", Blocks.CRACKED_NETHER_BRICKS.toSlab()).pickaxe()
    val SMOOTH_BASALT_SLAB = register("smooth_basalt_slab", Blocks.SMOOTH_BASALT.toSlab()).pickaxe()
    val CRACKED_POLISHED_BLACKSTONE_BRICKS_SLAB = register(
        "cracked_polished_blackstone_bricks_slab", Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.toSlab()
    ).pickaxe()
    val OBSIDIAN_SLAB =
        register("obsidian_slab", VSlabBlock(Blocks.OBSIDIAN, obsidian)).slab().pickaxe().needsDiamond()
    val CRYING_OBSIDIAN_SLAB = register("crying_obsidian_slab", CryingSlabBlock(Blocks.CRYING_OBSIDIAN, cryingObsidian))
        .slab().pickaxe().needsDiamond()
    val END_STONE_SLAB = register("end_stone_slab", Blocks.END_STONE.toSlab()).pickaxe()
    val QUARTZ_BRICKS_SLAB = register("quartz_bricks_slab", Blocks.QUARTZ_BRICKS.toSlab()).pickaxe()

    val SNOW_SLAB = register("snow_slab", Blocks.SNOW_BLOCK.toSlab()).shovel()

    // Walls
    val STONE_WALL = register("stone_wall", Blocks.STONE.toWall())
    val SMOOTH_STONE_WALL = register("smooth_stone_wall", Blocks.SMOOTH_STONE.toWall())
    val CRACKED_STONE_BRICKS_WALL =
        register("cracked_stone_bricks_wall", Blocks.CRACKED_STONE_BRICKS.toWall())
    val POLISHED_GRANITE_WALL = register("polished_granite_wall", Blocks.POLISHED_GRANITE.toWall())
    val POLISHED_DIORITE_WALL = register("polished_diorite_wall", Blocks.POLISHED_DIORITE.toWall())
    val POLISHED_ANDESITE_WALL = register("polished_andesite_wall", Blocks.POLISHED_ANDESITE.toWall())
    val CALCITE_WALL = register("calcite_wall", Blocks.CALCITE.toWall())
    val DRIPSTONE_WALL = register("dripstone_wall", Blocks.DRIPSTONE_BLOCK.toWall())
    val CRACKED_DEEPSLATE_BRICKS_WALL =
        register("cracked_deepslate_bricks_wall", Blocks.CRACKED_DEEPSLATE_BRICKS.toWall())
    val CRACKED_DEEPSLATE_TILES_WALL =
        register("cracked_deepslate_tiles_wall", Blocks.CRACKED_DEEPSLATE_TILES.toWall())
    val PACKED_MUD_WALL = register("packed_mud_wall", Blocks.PACKED_MUD.toWall())
    val SMOOTH_SANDSTONE_WALL = register("smooth_sandstone_wall", Blocks.SMOOTH_SANDSTONE.toWall())
    val CUT_SANDSTONE_WALL = register("cut_sandstone_wall", Blocks.CUT_SANDSTONE.toWall())
    val SMOOTH_RED_SANDSTONE_WALL =
        register("smooth_red_sandstone_wall", Blocks.SMOOTH_RED_SANDSTONE.toWall())
    val CUT_RED_SANDSTONE_WALL = register("cut_red_sandstone_wall", Blocks.CUT_RED_SANDSTONE.toWall())
    val PRISMARINE_BRICKS_WALL = register("prismarine_bricks_wall", Blocks.PRISMARINE_BRICKS.toWall())
    val DARK_PRISMARINE_WALL = register("dark_prismarine_wall", Blocks.DARK_PRISMARINE.toWall())
    val CRACKED_NETHER_BRICKS_WALL =
        register("cracked_nether_bricks_wall", Blocks.CRACKED_NETHER_BRICKS.toWall())
    val SMOOTH_BASALT_WALL = register("smooth_basalt_wall", Blocks.SMOOTH_BASALT.toWall())
    val CRACKED_POLISHED_BLACKSTONE_BRICKS_WALL = register(
        "cracked_polished_blackstone_bricks_wall", Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.toWall()
    )
    val OBSIDIAN_WALL = register("obsidian_wall", VWallBlock(Blocks.OBSIDIAN, obsidian)).needsDiamond().wall()
    val CRYING_OBSIDIAN_WALL = register("crying_obsidian_wall", CryingWallBlock(Blocks.CRYING_OBSIDIAN, cryingObsidian))
        .needsDiamond().wall()
    val END_STONE_WALL = register("end_stone_wall", Blocks.END_STONE.toWall())
    val PURPUR_WALL = register("purpur_wall", Blocks.PURPUR_BLOCK.toWall())
    val QUARTZ_WALL = register("quartz_wall", Blocks.QUARTZ_BLOCK.toWall())
    val QUARTZ_BRICKS_WALL = register("quartz_bricks_wall", Blocks.QUARTZ_BRICKS.toWall())
    val SMOOTH_QUARTZ_WALL = register("smooth_quartz_wall", Blocks.SMOOTH_QUARTZ.toWall())

    val SNOW_WALL = register("snow_wall", Blocks.SNOW_BLOCK.toWall()).shovel()


    val HEAVY_CUBE = register("heavy_cube", CompositeBlock(copy(Blocks.HEAVY_CORE).nonOpaque()))
        .pickaxe().cutout()
    // Buttons
//    val SMOOTH_STONE_BUTTON = createStoneBtn(Blocks.SMOOTH_STONE)
//    val DEEPSLATE_BUTTON = createStoneBtn(Blocks.DEEPSLATE)
//    val POLISHED_DEEPSLATE_BUTTON = createStoneBtn(Blocks.POLISHED_DEEPSLATE)
//    val BLACKSTONE_BUTTON = createStoneBtn(Blocks.BLACKSTONE)

    // Pressure plates
//    val SMOOTH_STONE_PRESSURE_PLATE = createStonePlate(Blocks.SMOOTH_STONE)
//    val DEEPSLATE_PRESSURE_PLATE = createStonePlate(Blocks.DEEPSLATE)
//    val POLISHED_DEEPSLATE_PRESSURE_PLATE = createStonePlate(Blocks.POLISHED_DEEPSLATE)
//    val BLACKSTONE_PRESSURE_PLATE = createStonePlate(Blocks.BLACKSTONE)


    // Polished Sandstone
    val POLISHED_SANDSTONE = register("polished_sandstone", Block(copy(Blocks.CUT_SANDSTONE))).pickaxe()
    val POLISHED_SANDSTONE_STAIRS = register("polished_sandstone_stairs", POLISHED_SANDSTONE.toStairs()).pickaxe()
    val POLISHED_SANDSTONE_SLAB = register("polished_sandstone_slab", POLISHED_SANDSTONE.toSlab()).pickaxe()
    val POLISHED_SANDSTONE_WALL = register("polished_sandstone_wall", POLISHED_SANDSTONE.toWall())

    val POLISHED_RED_SANDSTONE = register("polished_red_sandstone", Block(copy(Blocks.CUT_RED_SANDSTONE))).pickaxe()
    val POLISHED_RED_SANDSTONE_STAIRS = register("polished_red_sandstone_stairs", POLISHED_RED_SANDSTONE.toStairs()).pickaxe()
    val POLISHED_RED_SANDSTONE_SLAB = register("polished_red_sandstone_slab", POLISHED_RED_SANDSTONE.toSlab()).pickaxe()
    val POLISHED_RED_SANDSTONE_WALL = register("polished_red_sandstone_wall", POLISHED_RED_SANDSTONE.toWall())
    // Rough Sandstone
    val ROUGH_SANDSTONE = register("rough_sandstone", Block(copy(Blocks.SANDSTONE))).pickaxe()
    val ROUGH_SANDSTONE_STAIRS = register("rough_sandstone_stairs", ROUGH_SANDSTONE.toStairs()).pickaxe()
    val ROUGH_SANDSTONE_SLAB = register("rough_sandstone_slab", ROUGH_SANDSTONE.toSlab()).pickaxe()
    val ROUGH_SANDSTONE_WALL = register("rough_sandstone_wall", ROUGH_SANDSTONE.toWall())

    val ROUGH_RED_SANDSTONE = register("rough_red_sandstone", Block(copy(Blocks.RED_SANDSTONE))).pickaxe()
    val ROUGH_RED_SANDSTONE_STAIRS = register("rough_red_sandstone_stairs", ROUGH_RED_SANDSTONE.toStairs()).pickaxe()
    val ROUGH_RED_SANDSTONE_SLAB = register("rough_red_sandstone_slab", ROUGH_RED_SANDSTONE.toSlab()).pickaxe()
    val ROUGH_RED_SANDSTONE_WALL = register("rough_red_sandstone_wall", ROUGH_RED_SANDSTONE.toWall())

    fun init() = Unit

    fun register(id: String, item: Block): Block {
        val holder = registerNoItem(id, item)
        VVItems.registerBlockItem(id, holder)
        return holder
    }

    fun registerNoItem(id: String, item: Block): Block {
        val holder = Registries.BLOCK.register(id(id), item)
        BLOCKS.add(holder)
        return holder
    }
}