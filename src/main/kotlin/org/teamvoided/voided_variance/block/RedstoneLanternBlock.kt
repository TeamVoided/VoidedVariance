package org.teamvoided.voided_variance.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LanternBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty


class RedstoneLanternBlock(properties: Properties) : LanternBlock(properties) {

    init {
        registerDefaultState(defaultBlockState().setValue(LIT, true))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(LIT)
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super.onPlace(state, level, pos, oldState, notify)
        for (dir in Direction.entries) {
            level.updateNeighborsAt(pos.relative(dir), this)
        }
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, moved: Boolean) {
        super.onRemove(state, level, pos, newState, false)
        if (moved) return

        for (dir in Direction.entries) {
            level.updateNeighborsAt(pos.relative(dir), this)
        }
    }

    override fun neighborChanged(
        state: BlockState, level: Level, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean,
    ) {
        super.neighborChanged(state, level, pos, block, fromPos, notify)
        if (state.getValue(LIT) == shouldUnPower(level, pos, state) && !level.blockTicks.willTickThisTick(pos, this)) {
            level.scheduleTick(pos, this, 2)
        }
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, dir: Direction): Int {
        if (!state.getValue(LIT)) return 0

        return if ((state.getValue(HANGING) && Direction.DOWN != dir) || Direction.UP != dir) 15 else 0
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, dir: Direction): Int {
        return if (state.getValue(HANGING))
            if (dir == Direction.UP) state.getSignal(level, pos, dir) else 0
        else
            if (dir == Direction.DOWN) state.getSignal(level, pos, dir) else 0
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        level.setBlock(
            pos,
            state.setValue(LIT, !(state.getValue(LIT) && shouldUnPower(level, pos, state))),
            UPDATE_ALL
        )
        super.tick(state, level, pos, random)
    }

    override fun isSignalSource(state: BlockState): Boolean = state.getValue(LIT)

    companion object {

        val LIT: BooleanProperty = BlockStateProperties.LIT

        fun shouldUnPower(level: Level, pos: BlockPos, state: BlockState): Boolean {
            return if (state.getValue(HANGING))
                level.hasSignal(pos.above(), Direction.UP)
            else
                level.hasSignal(pos.below(), Direction.DOWN)
        }

    }
}