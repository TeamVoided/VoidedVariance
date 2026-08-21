package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TexturedModel
import org.teamvoided.voided_variance.utils.datagen.CARPET_DOWN as CARPET_DOWN_MODEL

object TexturedModels {
    val CARPET_DOWN: TexturedModel.Provider = TexturedModel.createDefault(TextureMapping::wool, CARPET_DOWN_MODEL)
}