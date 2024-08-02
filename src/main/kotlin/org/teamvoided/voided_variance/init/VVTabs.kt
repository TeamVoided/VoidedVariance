package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.utils.*
import org.teamvoided.voided_variance.utils.datagen.SLABS
import org.teamvoided.voided_variance.utils.datagen.STAIRS
import org.teamvoided.voided_variance.utils.datagen.WALLS

object VVTabs {
    val DUSK_AUTUMN_TAB = register("voided_variance",
        FabricItemGroup.builder()
            .icon { VVBlocks.REDSTONE_LANTERN.asItem().defaultStack }
            .name(Text.translatable("itemGroup.voided_variance.voided_variance"))
            .entries { _, entries ->
                if (isDev()) {
                    entries.addItems(VVBlocks.BLOCKS.map(Block::asItem))
                    return@entries
                }
                entries.addItem(
                    VVBlocks.BRICK_FENCE,

                    VVBlocks.INFESTED_MOSSY_COBBLESTONE,
                    VVBlocks.INFESTED_COBBLED_DEEPSLATE,
                    VVBlocks.INFESTED_DEEPSLATE_BRICKS,
                    VVBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS,
                    VVBlocks.INFESTED_DEEPSLATE_TILES,
                    VVBlocks.INFESTED_CRACKED_DEEPSLATE_TILES,
                    VVBlocks.INFESTED_POLISHED_DEEPSLATE,

                    VVBlocks.REDSTONE_LANTERN,
                )
                entries.addLists(STAIRS, SLABS, WALLS)
            }.build()
    )

    fun init() {
        addToTab(ItemGroups.BUILDING_BLOCKS) {
            it.addAfter(Blocks.BRICK_WALL, VVBlocks.BRICK_FENCE)

            it.addBefore(Blocks.STONE, Blocks.SNOW_BLOCK, VVBlocks.SNOW_STAIR, VVBlocks.SNOW_WALL)

            it.addAfter(Blocks.STONE_SLAB, VVBlocks.STONE_WALL)

            it.addAfter(Blocks.SMOOTH_STONE, VVBlocks.SMOOTH_STONE_STAIR)
            it.addAfter(Blocks.SMOOTH_STONE_SLAB, VVBlocks.SMOOTH_STONE_WALL)

            it.addAfter(
                Blocks.CRACKED_STONE_BRICKS,
                VVBlocks.CRACKED_STONE_BRICKS_STAIR,
                VVBlocks.CRACKED_STONE_BRICKS_WALL,
                VVBlocks.CRACKED_STONE_BRICKS_SLAB
            )

            it.addAfter(Blocks.POLISHED_GRANITE_SLAB, VVBlocks.POLISHED_GRANITE_WALL)
            it.addAfter(Blocks.POLISHED_DIORITE_SLAB, VVBlocks.POLISHED_DIORITE_WALL)
            it.addAfter(
                Blocks.POLISHED_ANDESITE_SLAB, VVBlocks.POLISHED_ANDESITE_WALL,
                // More rocks
                Blocks.DRIPSTONE_BLOCK, VVBlocks.DRIPSTONE_STAIR, VVBlocks.DRIPSTONE_SLAB, VVBlocks.DRIPSTONE_WALL,
                Blocks.CALCITE, VVBlocks.CALCITE_STAIR, VVBlocks.CALCITE_SLAB, VVBlocks.CALCITE_WALL
            )

            it.addAfter(
                Blocks.CRACKED_DEEPSLATE_BRICKS,
                VVBlocks.CRACKED_DEEPSLATE_BRICKS_STAIR,
                VVBlocks.CRACKED_DEEPSLATE_BRICKS_SLAB,
                VVBlocks.CRACKED_DEEPSLATE_BRICKS_WALL
            )
            it.addAfter(
                Blocks.CRACKED_DEEPSLATE_TILES,
                VVBlocks.CRACKED_DEEPSLATE_TILES_STAIR,
                VVBlocks.CRACKED_DEEPSLATE_TILES_SLAB,
                VVBlocks.CRACKED_DEEPSLATE_TILES_WALL
            )

            it.addAfter(
                Blocks.PACKED_MUD, VVBlocks.PACKED_MUD_STAIR, VVBlocks.PACKED_MUD_SLAB, VVBlocks.PACKED_MUD_WALL
            )

            it.addAfter(Blocks.SMOOTH_SANDSTONE_SLAB, VVBlocks.SMOOTH_SANDSTONE_WALL)
            it.addAfter(Blocks.CUT_SANDSTONE, VVBlocks.CUT_SANDSTONE_STAIR)
            it.addAfter(Blocks.CUT_SANDSTONE_SLAB, VVBlocks.CUT_SANDSTONE_WALL)

            it.addAfter(Blocks.SMOOTH_RED_SANDSTONE_SLAB, VVBlocks.SMOOTH_RED_SANDSTONE_WALL)
            it.addAfter(Blocks.CUT_RED_SANDSTONE, VVBlocks.CUT_RED_SANDSTONE_STAIR)
            it.addAfter(Blocks.CUT_RED_SANDSTONE_SLAB, VVBlocks.CUT_RED_SANDSTONE_WALL)


            it.addAfter(Blocks.PRISMARINE_BRICK_SLAB, VVBlocks.PRISMARINE_BRICKS_WALL)
            it.addAfter(Blocks.DARK_PRISMARINE_SLAB, VVBlocks.DARK_PRISMARINE_WALL)

            it.addAfter(
                Blocks.CRACKED_NETHER_BRICKS,
                VVBlocks.CRACKED_NETHER_BRICKS_STAIR,
                VVBlocks.CRACKED_NETHER_BRICKS_SLAB,
                VVBlocks.CRACKED_NETHER_BRICKS_WALL
            )
            it.addAfter(
                Blocks.SMOOTH_BASALT,
                VVBlocks.SMOOTH_BASALT_STAIR, VVBlocks.SMOOTH_BASALT_SLAB, VVBlocks.SMOOTH_BASALT_WALL
            )
            it.addAfter(
                Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                VVBlocks.CRACKED_POLISHED_BLACKSTONE_BRICKS_STAIR,
                VVBlocks.CRACKED_POLISHED_BLACKSTONE_BRICKS_SLAB,
                VVBlocks.CRACKED_POLISHED_BLACKSTONE_BRICKS_WALL
            )

            it.addBefore(
                Blocks.END_STONE,
                Blocks.OBSIDIAN, VVBlocks.OBSIDIAN_STAIR, VVBlocks.OBSIDIAN_SLAB, VVBlocks.OBSIDIAN_WALL
            )
            it.addAfter(
                Blocks.END_STONE,
                VVBlocks.END_STONE_STAIR, VVBlocks.END_STONE_SLAB, VVBlocks.END_STONE_WALL
            )
            it.addAfter(Blocks.PURPUR_SLAB, VVBlocks.PURPUR_WALL)

            it.addAfter(Blocks.QUARTZ_SLAB, VVBlocks.QUARTZ_WALL)
            it.addAfter(
                Blocks.QUARTZ_BRICKS,
                VVBlocks.QUARTZ_BRICKS_STAIR, VVBlocks.QUARTZ_BRICKS_SLAB, VVBlocks.QUARTZ_BRICKS_WALL
            )

            it.addAfter(Blocks.SMOOTH_QUARTZ_SLAB, VVBlocks.SMOOTH_QUARTZ_WALL)
        }

        addToTab(ItemGroups.FUNCTIONAL_BLOCKS) {
            it.addBefore(Blocks.CHAIN, VVBlocks.REDSTONE_LANTERN)

            it.addAfter(Blocks.INFESTED_COBBLESTONE, VVBlocks.INFESTED_MOSSY_COBBLESTONE)
            it.addAfter(
                Blocks.INFESTED_DEEPSLATE,
                VVBlocks.INFESTED_COBBLED_DEEPSLATE,
                VVBlocks.INFESTED_DEEPSLATE_BRICKS,
                VVBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS,
                VVBlocks.INFESTED_DEEPSLATE_TILES,
                VVBlocks.INFESTED_CRACKED_DEEPSLATE_TILES,
                VVBlocks.INFESTED_POLISHED_DEEPSLATE,
            )
        }
    }


    @Suppress("SameParameterValue")
    private fun register(name: String, itemGroup: ItemGroup): Holder.Reference<ItemGroup> {
        return Registries.ITEM_GROUP.registerHolder(id(name), itemGroup)
    }

}
