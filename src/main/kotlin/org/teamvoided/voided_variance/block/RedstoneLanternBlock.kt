package org.teamvoided.voided_variance.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.LanternBlock
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.random.RandomGenerator
import net.minecraft.world.BlockView
import net.minecraft.world.World

class RedstoneLanternBlock(settings: Settings) : LanternBlock(settings) {
    init {
        defaultState = stateManager.defaultState.with(LIT, true)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(LIT)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        for (direction in Direction.entries) {
            world.updateNeighborsAlways(pos.offset(direction), this)
        }
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (moved) return
        for (direction in Direction.entries) {
            world.updateNeighborsAlways(pos.offset(direction), this)
        }
    }

    override fun neighborUpdate(
        state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean,
    ) {
        if (state.get(LIT) == this.shouldUnPower(world, pos, state) && !world.blockTickScheduler.willTick(pos, this))
            world.scheduleBlockTick(pos, this, 2)
    }

    override fun getWeakRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction): Int {
        if (!state.get(LIT)) return 0

        return if ((state.get(HANGING) && Direction.DOWN != direction) || Direction.UP != direction) 15
        else 0
    }

    override fun getStrongRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, dir: Direction): Int {
        if (state.get(HANGING)) {
            return if (dir == Direction.UP) state.getWeakRedstonePower(world, pos, dir) else 0
        }
        return if (dir == Direction.DOWN) state.getWeakRedstonePower(world, pos, dir) else 0
    }

    private fun shouldUnPower(world: World, pos: BlockPos, state: BlockState): Boolean {
        val isHanging = state.get(HANGING)

        return if (isHanging) world.isEmittingRedstonePower(pos.up(), Direction.UP)
        else world.isEmittingRedstonePower(pos.down(), Direction.DOWN)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: RandomGenerator) {
        world.setBlockState(
            pos,
            state.with(LIT, !(state.get(LIT) && shouldUnPower(world, pos, state))),
            3
        )
    }

    override fun isRedstonePowerSource(state: BlockState): Boolean = state.get(LIT)

    companion object {
        val LIT: BooleanProperty = Properties.LIT
        val HANGING: BooleanProperty = Properties.HANGING
    }
}