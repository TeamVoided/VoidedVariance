package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureSlot
import org.teamvoided.voided_variance.VoidedVariance.id


fun block(parent: String, variant: String, vararg requiredTextures: TextureSlot): ModelTemplate {
    return ModelTemplate(id("block/$parent").myb(), variant.myb(), *requiredTextures)
}

val OFFSET_WALL_POST = block("template/offset/wall_post", "_post", TextureSlot.WALL)
val OFFSET_WALL_SIDE = block("template/offset/wall_side", "_side", TextureSlot.WALL)
val OFFSET_WALL_SIDE_TALL = block("template/offset/wall_side_tall", "_side_tall", TextureSlot.WALL)
val OFFSET_WALL_INVENTORY = block("template/offset/wall_inventory", "_inventory", TextureSlot.WALL)

val CARPET_DOWN = block("template/carpet_down", "_down", TextureSlot.WOOL)
