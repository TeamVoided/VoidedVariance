package org.teamvoided.voided_variance.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape


open class CarpetPlateBlock(type: BlockSetType, properties: Properties) : PressurePlateBlock(type, properties) {

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, ctx: CollisionContext): VoxelShape {
        return if (getSignalForState(state) > 0) PRESSED_SHAPE else DEFAULT_SHAPE
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return !level.isEmptyBlock(pos.below())
    }

    companion object {

        val PRESSED_SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 0.5, 16.0)
        val DEFAULT_SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)

    }
}