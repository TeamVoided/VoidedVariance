package org.teamvoided.voided_variance.init

import net.minecraft.block.Block
import net.minecraft.block.dispenser.DispenserBlock
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.PotionContentsComponent
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.item.CustomGlassBottleItem
import org.teamvoided.voided_variance.item.TintedLingeringPotionItem
import org.teamvoided.voided_variance.item.TintedPotionItem
import org.teamvoided.voided_variance.item.TintedSplashPotionItem
import org.teamvoided.voided_variance.utils.register

object VVItems {
    val ITEMS = mutableListOf<Item>()


    val TINTED_POTION = register("tinted_potion", TintedPotionItem(potionSetting()))
    val TINTED_GLASS_BOTTLE = register("tinted_glass_bottle", CustomGlassBottleItem(TINTED_POTION, Item.Settings()))

    val TINTED_SPLASH_POTION = register("tinted_splash_potion", TintedSplashPotionItem(potionSetting()))
    val TINTED_LINGERING_POTION = register("tinted_lingering_potion", TintedLingeringPotionItem(potionSetting()))

    fun init() {
        DispenserBlock.registerBehavior(TINTED_SPLASH_POTION)
        DispenserBlock.registerBehavior(TINTED_LINGERING_POTION)
    }

    fun register(id: String, item: Item): Item {
        val holder = Registries.ITEM.register(id(id), item)
        ITEMS.add(holder)
        return holder
    }

    fun potionSetting() =
        Item.Settings().maxCount(1).component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)

    fun registerBlockItem(id: String, block: Block) = register(id, BlockItem(block, Item.Settings()))
}