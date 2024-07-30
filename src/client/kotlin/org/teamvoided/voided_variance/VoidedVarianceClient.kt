package org.teamvoided.voided_variance

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer


@Suppress("unused")
object VoidedVarianceClient {
    private val CUTOUTS = listOf<Block>()

    fun init() {
        CUTOUTS.forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderLayer.getCutout()) }
    }
}
