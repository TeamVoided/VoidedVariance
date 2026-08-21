package org.teamvoided.voided_variance

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.component.DataComponents
import net.minecraft.util.FastColor
import net.minecraft.world.item.alchemy.PotionContents
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.datagen.CUTOUT_BLOCKS


@Suppress("unused")
object VoidedVarianceClient {

    fun init() {
        CUTOUT_BLOCKS.forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderType.cutout()) }
        listOf(VVBlocks.TINTED_GLASS_PANE)
            .forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderType.translucent()) }

        ColorProviderRegistry.ITEM.register(
            { stack, tintIdx ->
                if (tintIdx > 0)
                    -1
                else
                    FastColor.ARGB32.opaque(
                        FastColor.ARGB32.multiply(
                            stack.getOrDefault(
                                DataComponents.POTION_CONTENTS,
                                PotionContents.EMPTY
                            ).color, 0xFF_7F_7F_7F.toInt()
                        )
                    )
            },
            VVItems.TINTED_POTION, VVItems.TINTED_SPLASH_POTION, VVItems.TINTED_LINGERING_POTION
        )
    }
}
