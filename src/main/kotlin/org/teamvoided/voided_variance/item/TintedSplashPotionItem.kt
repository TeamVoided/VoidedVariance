package org.teamvoided.voided_variance.item

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SplashPotionItem
import net.minecraft.world.item.TooltipFlag
import org.teamvoided.voided_variance.utils.appendTintedTooltip

class TintedSplashPotionItem(properties: Properties) : SplashPotionItem(properties) {

    override fun getDescriptionId(stack: ItemStack): String = super.getDescriptionId()

    override fun appendHoverText(
        stack: ItemStack, ctx: TooltipContext, tooltips: MutableList<Component>, flag: TooltipFlag,
    ) {
        appendTintedTooltip(stack, tooltips)
    }

}