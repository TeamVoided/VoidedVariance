
package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy
import org.teamvoided.voided_variance.block.*

val CUTOUT_BLOCKS = mutableSetOf<Block>()

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


fun Block.needsDiamond(): Block {
    NEEDS_DIAMOND.add(this)
    return this
}

fun Block.toStairs(): Block {
    val block = VStairsBlock(this, ofFullCopy(this))
    STAIRS.add(block)
    return block
}

fun Block.toSlab(): Block {
    val block = VSlabBlock(this, ofFullCopy(this))
    SLABS.add(block)
    return block
}

fun Block.toWall(): Block {
    val block = VWallBlock(this, ofFullCopy(this))
    WALLS.add(block)
    return block
}


fun Block.wall(): Block {
    WALLS.add(this)
    return this
}

fun Block.stairs(): Block {
    STAIRS.add(this)
    return this
}

fun Block.slab(): Block {
    SLABS.add(this)
    return this
}