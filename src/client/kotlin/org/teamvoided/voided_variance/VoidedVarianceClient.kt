package org.teamvoided.voided_variance

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.util.ColorUtil
import net.minecraft.client.util.ColorUtil.Argb32.mixColor
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.PotionContentsComponent
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.datagen.CUTOUT_BLOCKS


@Suppress("unused")
object VoidedVarianceClient {

    fun init() {
        CUTOUT_BLOCKS.forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderLayer.getCutout()) }
        listOf(VVBlocks.TINTED_GLASS_PANE)
            .forEach { BlockRenderLayerMap.INSTANCE.putBlock(it, RenderLayer.getTranslucent()) }

        ColorProviderRegistry.ITEM.register(
            { stack, tintIdx ->
                if (tintIdx > 0) -1 else
                    ColorUtil.Argb32.toOpaque(
                        mixColor(
                            stack.getOrDefault(
                                DataComponentTypes.POTION_CONTENTS,
                                PotionContentsComponent.DEFAULT
                            ).color, 0xFF_7F_7F_7F.toInt()
                        )
                    )
            },
            VVItems.TINTED_POTION, VVItems.TINTED_SPLASH_POTION, VVItems.TINTED_LINGERING_POTION
        )
    }
}
