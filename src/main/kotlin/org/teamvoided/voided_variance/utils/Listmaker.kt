package org.teamvoided.voided_variance.utils

import net.minecraft.block.Block

val BOOKSHELFS = mutableSetOf<Block>()
fun Block.bookshelf(): Block {
    BOOKSHELFS.add(this)
    return this
}