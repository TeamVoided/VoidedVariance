package org.teamvoided.voided_variance.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock

open class VSlabBlock(val block: Block, properties: Properties) : SlabBlock(properties)
open class VWallBlock(val block: Block, properties: Properties) : WallBlock(properties)
open class VStairsBlock(block: Block, properties: Properties) : StairBlock(block.defaultBlockState(), properties) {
    val block: Block get() = baseState.block
}
