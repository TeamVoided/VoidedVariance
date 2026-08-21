package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.`object`.builder.v1.block.type.BlockSetTypeBuilder
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.properties.BlockSetType
import org.teamvoided.voided_variance.VoidedVariance.id

object VVBlockSetTypes {
    fun init() = Unit
    val WOOL = create("wool") {
        it
            .openableByHand(true)
            .openableByWindCharge(true)
            .buttonActivatedByArrows(true)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.EVERYTHING)
            .soundGroup(SoundType.WOOL)
            .doorCloseSound(SoundEvents.CHERRY_WOOD_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.CHERRY_WOOD_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.WOOL_HIT)
            .pressurePlateClickOnSound(SoundEvents.WOOL_FALL)
            .buttonClickOffSound(SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON)

    }
    val MOSS = create("moss") {
        it
            .openableByHand(true)
            .openableByWindCharge(true)
            .buttonActivatedByArrows(true)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.EVERYTHING)
            .soundGroup(SoundType.MOSS_CARPET)
            .doorCloseSound(SoundEvents.CHERRY_WOOD_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.CHERRY_WOOD_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.MOSS_CARPET_HIT)
            .pressurePlateClickOnSound(SoundEvents.MOSS_CARPET_FALL)
            .buttonClickOffSound(SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON)

    }


    fun create(name: String, fn: (builder: BlockSetTypeBuilder) -> BlockSetTypeBuilder): BlockSetType =
        fn(BlockSetTypeBuilder()).build(id(name))


}