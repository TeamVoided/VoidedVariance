package org.teamvoided.voided_variance.init

import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DispenserBlock
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.item.CustomGlassBottleItem
import org.teamvoided.voided_variance.item.TintedLingeringPotionItem
import org.teamvoided.voided_variance.item.TintedPotionItem
import org.teamvoided.voided_variance.item.TintedSplashPotionItem
import org.teamvoided.voided_variance.utils.register

object VVItems {

    val ITEMS = mutableListOf<Item>()


    val TINTED_POTION = register("tinted_potion", TintedPotionItem(potionSetting()))
    val TINTED_GLASS_BOTTLE = register("tinted_glass_bottle", CustomGlassBottleItem(TINTED_POTION, Item.Properties()))

    val TINTED_SPLASH_POTION = register("tinted_splash_potion", TintedSplashPotionItem(potionSetting()))
    val TINTED_LINGERING_POTION = register("tinted_lingering_potion", TintedLingeringPotionItem(potionSetting()))

    fun init() {
        DispenserBlock.registerProjectileBehavior(TINTED_SPLASH_POTION)
        DispenserBlock.registerProjectileBehavior(TINTED_LINGERING_POTION)
    }

    fun register(id: String, item: Item): Item {
        val holder = BuiltInRegistries.ITEM.register(id(id), item)
        ITEMS.add(holder)
        return holder
    }

    fun potionSetting(): Item.Properties =
        Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)

    fun registerBlockItem(id: String, block: Block) = register(id, BlockItem(block, Item.Properties()))
}