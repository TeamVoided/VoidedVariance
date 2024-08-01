package org.teamvoided.voided_variance

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer
import org.teamvoided.voided_variance.init.VVBlocks


@Suppress("unused")
object VoidedVarianceClient {

    fun init() {
        VVBlocks.CUTOUT_BLOCKS.forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderLayer.getCutout()) }
    }
}
