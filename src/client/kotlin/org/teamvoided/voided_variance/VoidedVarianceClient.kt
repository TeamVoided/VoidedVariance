package org.teamvoided.voided_variance

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer
import org.teamvoided.voided_variance.utils.datagen.CUTOUT_BLOCKS


@Suppress("unused")
object VoidedVarianceClient {

    fun init() {
        CUTOUT_BLOCKS.forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderLayer.getCutout()) }
    }
}
