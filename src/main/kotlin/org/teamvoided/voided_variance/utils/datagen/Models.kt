package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.client.model.Model
import net.minecraft.data.client.model.TextureKey
import org.teamvoided.voided_variance.VoidedVariance.id


fun block(parent: String, variant: String, vararg requiredTextures: TextureKey): Model =
    Model(id("block/$parent").myb(), variant.myb(), *requiredTextures)

val OFFSET_WALL_POST = block("template/offset/wall_post", "_post", TextureKey.WALL)
val OFFSET_WALL_SIDE = block("template/offset/wall_side", "_side", TextureKey.WALL)
val OFFSET_WALL_SIDE_TALL = block("template/offset/wall_side_tall", "_side_tall", TextureKey.WALL)
val OFFSET_WALL_INVENTORY = block("template/offset/wall_inventory", "_inventory", TextureKey.WALL)

val CARPET_DOWN = block("template/carpet_down", "_down", TextureKey.WOOL)
