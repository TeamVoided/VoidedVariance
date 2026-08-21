package org.teamvoided.voided_variance.utils

import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import org.teamvoided.voided_variance.VoidedVariance.MODID

const val HEAVY_CUBE_TOOLTIP = "block.$MODID.heavy_cube.tooltip"
const val TINTED_TOOLTIP = "$MODID.tinted_potion.tooltip"

fun appendTintedTooltip(stack: ItemStack, tooltips: MutableList<Component>) {
    stack.get(DataComponents.POTION_CONTENTS)?.let {
        tooltips.add(
            Component.translatable(TINTED_TOOLTIP).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY)
        )
    }
}
