package org.teamvoided.voidlib.helpers

import net.minecraft.block.BlockState
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun World.playSound(pos: BlockPos, sound: SoundEvent, category: SoundCategory, volume: Float, pitch: Float) =
    this.playSound(pos, sound, category, volume, pitch, false)


fun World.playBlockSound(pos: BlockPos, sound: SoundEvent, volume: Float, pitch: Float) =
    this.playSound(pos, sound, SoundCategory.BLOCKS, volume, pitch, false)

fun World.scheduleFluidTick(pos: BlockPos, state: BlockState) =
    this.scheduleFluidTick(pos, state.fluidState.fluid, state.fluidState.fluid.getTickRate(this))

