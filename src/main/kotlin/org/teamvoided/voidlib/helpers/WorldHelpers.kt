package org.teamvoided.voidlib.helpers

import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun World.playSound(pos: BlockPos, sound: SoundEvent, category: SoundCategory, volume: Float, pitch: Float) =
    this.playSound(pos, sound, category, volume, pitch, false)


fun World.playBlockSound(pos: BlockPos, sound: SoundEvent, volume: Float, pitch: Float) =
    this.playSound(pos, sound, SoundCategory.BLOCKS, volume, pitch, false)