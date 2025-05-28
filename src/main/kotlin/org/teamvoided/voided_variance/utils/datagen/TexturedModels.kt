package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TexturedModel
import org.teamvoided.voided_variance.utils.datagen.CARPET_DOWN as CARPET_DOWN_MODEL

object TexturedModels {
    val CARPET_DOWN = TexturedModel.makeFactory({ block -> Texture.wool(block) }, CARPET_DOWN_MODEL)
}
