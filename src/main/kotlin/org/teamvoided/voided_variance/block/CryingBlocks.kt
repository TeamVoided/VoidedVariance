package org.teamvoided.voided_variance.block

import net.minecraft.block.*
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.random.RandomGenerator
import net.minecraft.world.World

interface CryingBlock {
    fun particle(state: BlockState, world: World, pos: BlockPos, random: RandomGenerator) {
        if (random.nextInt(5) == 0) {
        val direction = Direction.random(random)
        if (direction != Direction.UP) {
            val blockPos = pos.offset(direction)
            val blockState = world.getBlockState(blockPos)
            if ( !state.isOpaque || !blockState.isSideSolidFullSquare(world, blockPos, direction.opposite)) {
                val d =
                    if (direction.offsetX == 0) random.nextDouble() else 0.5 + direction.offsetX.toDouble() * 0.6
                val e =
                    if (direction.offsetY == 0) random.nextDouble() else 0.5 + direction.offsetY.toDouble() * 0.6
                val f =
                    if (direction.offsetZ == 0) random.nextDouble() else 0.5 + direction.offsetZ.toDouble() * 0.6
                println("spawned")
                world.addParticle(
                    ParticleTypes.DRIPPING_OBSIDIAN_TEAR,
                    pos.x.toDouble() + d, pos.y.toDouble() + e, pos.z.toDouble() + f,
                    0.0, 0.0, 0.0
                )
            }
        }
        }
    }
}

class CryingStairsBlock(block: Block, settings: Settings) : VStairsBlock(block, settings), CryingBlock {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: RandomGenerator) {
        particle(state, world, pos, random)
        super.randomDisplayTick(state, world, pos, random)
    }
}

class CryingSlabBlock(block: Block, settings: Settings) : VSlabBlock(block, settings), CryingBlock {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: RandomGenerator) {
        particle(state, world, pos, random)
        super.randomDisplayTick(state, world, pos, random)
    }
}

class CryingWallBlock(block: Block, settings: Settings) : VWallBlock(block, settings), CryingBlock {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: RandomGenerator) {
        particle(state, world, pos, random)
        super.randomDisplayTick(state, world, pos, random)
    }
}
