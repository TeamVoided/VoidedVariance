package org.teamvoided.voided_variance.item

import net.minecraft.client.item.TooltipConfig
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.item.LingeringPotionItem
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.teamvoided.voided_variance.utils.TINTED_TOOLTIP

class TintedLingeringPotionItem(settings: Settings) : LingeringPotionItem(settings) {
    override fun getTranslationKey(stack: ItemStack?): String = super.getTranslationKey()
    override fun appendTooltip(
        stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>, config: TooltipConfig,
    ) {
        val potion = stack.get(DataComponentTypes.POTION_CONTENTS)
        if (potion != null) tooltip.add(
            Text.translatable(TINTED_TOOLTIP).formatted(Formatting.ITALIC, Formatting.DARK_GRAY)
        )
    }
}
