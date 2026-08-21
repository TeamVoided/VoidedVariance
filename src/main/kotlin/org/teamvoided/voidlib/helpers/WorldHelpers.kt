package org.teamvoided.voidlib.helpers

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState


fun Level.playBlockSound(pos: BlockPos, sound: SoundEvent, volume: Float, pitch: Float) {
    playLocalSound(pos, sound, SoundSource.BLOCKS, volume, pitch, false)
}

fun Level.scheduleFluidTick(pos: BlockPos, state: BlockState) {
    scheduleTick(pos, state.fluidState.type, state.fluidState.type.getTickDelay(this))
}

