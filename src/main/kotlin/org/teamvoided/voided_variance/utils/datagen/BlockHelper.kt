@file:Suppress("unused")

package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.block.AbstractBlock.Settings.copy
import net.minecraft.block.Block
import org.teamvoided.voided_variance.block.VSlabBlock
import org.teamvoided.voided_variance.block.VStairsBlock
import org.teamvoided.voided_variance.block.VWallBlock

val CUTOUT_BLOCKS = mutableSetOf<Block>()

val SWORDABLE = mutableSetOf<Block>()
val PICKAXABLE = mutableSetOf<Block>()
val AXABLE = mutableSetOf<Block>()
val SHOVELABLE = mutableSetOf<Block>()
val HOEABLE = mutableSetOf<Block>()

val STAIRS = mutableSetOf<Block>()
val SLABS = mutableSetOf<Block>()
val WALLS = mutableSetOf<Block>()

val NEEDS_STONE = mutableSetOf<Block>()
val NEEDS_IRON = mutableSetOf<Block>()
val NEEDS_DIAMOND = mutableSetOf<Block>()

fun Block.cutout(): Block {
    CUTOUT_BLOCKS.add(this)
    return this
}

fun Block.sword(): Block {
    SWORDABLE.add(this)
    return this
}

fun Block.pickaxe(): Block {
    PICKAXABLE.add(this)
    return this
}

fun Block.axe(): Block {
    AXABLE.add(this)
    return this
}

fun Block.shovel(): Block {
    SHOVELABLE.add(this)
    return this
}

fun Block.hoe(): Block {
    HOEABLE.add(this)
    return this
}

fun Block.needsStone(): Block {
    NEEDS_STONE.add(this)
    return this
}

fun Block.needsIron(): Block {
    NEEDS_IRON.add(this)
    return this
}

fun Block.needsDiamond(): Block {
    NEEDS_DIAMOND.add(this)
    return this
}

fun Block.toStair(): Block {
    val block = VStairsBlock(this, copy(this))
    STAIRS.add(block)
    return block
}

fun Block.toSlab(): Block {
    val block = VSlabBlock(this, copy(this))
    SLABS.add(block)
    return block
}

fun Block.toWall(): Block {
    val block = VWallBlock(this, copy(this))
    WALLS.add(block)
    return block
}