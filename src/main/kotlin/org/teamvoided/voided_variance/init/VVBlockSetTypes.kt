package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.`object`.builder.v1.block.type.BlockSetTypeBuilder
import net.minecraft.block.BlockSetType
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvents
import org.teamvoided.voided_variance.VoidedVariance.id

object VVBlockSetTypes {
    fun init() = Unit
    val WOOL = create("wool") {
        it
            .openableByHand(true)
            .openableByWindCharge(true)
            .buttonActivatedByArrows(true)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.EVERYTHING)
            .soundGroup(BlockSoundGroup.WOOL)
            .doorCloseSound(SoundEvents.BLOCK_CHERRY_WOOD_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.BLOCK_CHERRY_WOOD_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.BLOCK_WOOL_HIT)
            .pressurePlateClickOnSound(SoundEvents.BLOCK_WOOL_FALL)
            .buttonClickOffSound(SoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_ON)

    }
    val MOSS = create("moss") {
        it
            .openableByHand(true)
            .openableByWindCharge(true)
            .buttonActivatedByArrows(true)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.EVERYTHING)
            .soundGroup(BlockSoundGroup.MOSS_CARPET)
            .doorCloseSound(SoundEvents.BLOCK_CHERRY_WOOD_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.BLOCK_CHERRY_WOOD_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.BLOCK_MOSS_CARPET_HIT)
            .pressurePlateClickOnSound(SoundEvents.BLOCK_MOSS_CARPET_FALL)
            .buttonClickOffSound(SoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_ON)

    }


    fun create(name: String, fn: (builder: BlockSetTypeBuilder) -> BlockSetTypeBuilder): BlockSetType =
        fn(BlockSetTypeBuilder()).build(id(name))


}