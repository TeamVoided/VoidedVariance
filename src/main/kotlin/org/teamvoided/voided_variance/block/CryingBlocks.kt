package org.teamvoided.voided_variance.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState


interface CryingBlock {

    fun createParticles(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(5) != 0) return

        val dir = Direction.getRandom(random)
        if (dir == Direction.UP) return

        val offsetPos = pos.relative(dir)
        val sideSate = level.getBlockState(offsetPos)
        if (!state.canOcclude() || !sideSate.isFaceSturdy(level, offsetPos, dir.opposite)) {
            val xOffset = if (dir.stepX == 0) random.nextDouble() else 0.5 + dir.stepX * 0.6
            val yOffset = if (dir.stepY == 0) random.nextDouble() else 0.5 + dir.stepY * 0.6
            val zOffset = if (dir.stepZ == 0) random.nextDouble() else 0.5 + dir.stepZ * 0.6
            level.addParticle(
                getParticleOption(state, level, pos, random),
                pos.x + xOffset, pos.y + yOffset, pos.z + zOffset,
                0.0, 0.0, 0.0
            )
        }
    }

    fun getParticleOption(state: BlockState, level: Level, pos: BlockPos, random: RandomSource): ParticleOptions {
        return ParticleTypes.DRIPPING_OBSIDIAN_TEAR
    }

}

class CryingStairsBlock(block: Block, properties: Properties) : VStairsBlock(block, properties), CryingBlock {

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        createParticles(state, level, pos, random)
        super.animateTick(state, level, pos, random)
    }

}

class CryingSlabBlock(block: Block, properties: Properties) : VSlabBlock(block, properties), CryingBlock {

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        createParticles(state, level, pos, random)
        super.animateTick(state, level, pos, random)
    }

}

class CryingWallBlock(block: Block, properties: Properties) : VWallBlock(block, properties), CryingBlock {

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        createParticles(state, level, pos, random)
        super.animateTick(state, level, pos, random)
    }

}
