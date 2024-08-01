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

object VVTabs {
    val DUSK_AUTUMN_TAB = register("voided_variance",
        FabricItemGroup.builder()
            .icon { VVBlocks.BRICK_FENCE.asItem().defaultStack }
            .name(Text.translatable("itemGroup.voided_variance.voided_variance"))
            .entries { _, entries ->
                if (isDev()) {
                    entries.addItems(VVBlocks.BLOCKS.map(Block::asItem))
                    return@entries
                }

                entries.addItem(VVBlocks.BRICK_FENCE)

                entries.addItem(
                    VVBlocks.INFESTED_MOSSY_COBBLESTONE,
                    VVBlocks.INFESTED_COBBLED_DEEPSLATE,
                    VVBlocks.INFESTED_DEEPSLATE_BRICKS,
                    VVBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS,
                    VVBlocks.INFESTED_DEEPSLATE_TILES,
                    VVBlocks.INFESTED_CRACKED_DEEPSLATE_TILES,
                    VVBlocks.INFESTED_POLISHED_DEEPSLATE,
                )

            }.build()
    )

    fun init() {
        addToTab(ItemGroups.BUILDING_BLOCKS) {
            it.addAfter(Blocks.BRICK_WALL, VVBlocks.BRICK_FENCE)
        }
        addToTab(ItemGroups.FUNCTIONAL_BLOCKS) {
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
