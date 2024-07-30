package org.teamvoided.voided_variance.init

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.utils.register

object VVItems {
    val ITEMS = mutableListOf<Item>()



    fun init() = Unit

    fun register(id: String, item: Item): Item {
        val holder = Registries.ITEM.register(id(id), item)
        ITEMS.add(holder)
        return holder
    }

    fun registerBlockItem(id: String, block: Block) = register(id, BlockItem(block, Item.Settings()))
}