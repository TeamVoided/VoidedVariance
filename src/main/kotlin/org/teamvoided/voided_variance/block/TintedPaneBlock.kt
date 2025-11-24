package org.teamvoided.voided_variance.block

import net.minecraft.block.BlockState
import net.minecraft.block.PaneBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

class TintedPaneBlock(settings: Settings) : PaneBlock(settings) {
    override fun isTransparent(state: BlockState, world: BlockView, pos: BlockPos): Boolean = false
    override fun getOpacity(state: BlockState, world: BlockView, pos: BlockPos): Int = world.maxLightLevel / 2
}