package org.teamvoided.voided_variance.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.IronBarsBlock
import net.minecraft.world.level.block.state.BlockState


class TintedPaneBlock(properties: Properties) : IronBarsBlock(properties) {

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean = false

    override fun getLightBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Int = level.maxLightLevel / 2

}