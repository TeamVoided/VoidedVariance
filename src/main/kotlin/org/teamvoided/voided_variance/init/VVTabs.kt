package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.utils.addItems
import org.teamvoided.voided_variance.utils.registerHolder

object VVTabs {
    val DUSK_AUTUMN_TAB = register("voided_variance",
        FabricItemGroup.builder()
            .icon { VVBlocks.BRICK_FENCE.asItem().defaultStack }
            .name(Text.translatable("itemGroup.voided_variance.voided_variance"))
            .entries { _, entries ->
                entries.addItems(VVBlocks.BLOCKS.map(Block::asItem))
            }.build()
    )

    fun init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
            .register(ItemGroupEvents.ModifyEntries {
                it.addAfter(Blocks.BRICK_WALL, VVBlocks.BRICK_FENCE)
            })
    }


    @Suppress("SameParameterValue")
    private fun register(name: String, itemGroup: ItemGroup): Holder.Reference<ItemGroup> {
        return Registries.ITEM_GROUP.registerHolder(id(name), itemGroup)
    }

}
