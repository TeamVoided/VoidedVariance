package org.teamvoided.voided_variance.block

import net.minecraft.block.BlockSetType
import net.minecraft.block.BlockState
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.WorldView

open class CarpetPlateBlock(blockSetType: BlockSetType, settings: Settings) :
    PressurePlateBlock(blockSetType, settings) {
    override fun getOutlineShape(
        state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext,
    ): VoxelShape = if (this.getRedstoneOutput(state) > 0) PRESSED_SHAPE else DEFAULT_SHAPE

    companion object {
        val PRESSED_SHAPE: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 0.5, 16.0)
        val DEFAULT_SHAPE: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
    }

    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean = !world.isAir(pos.down())
}