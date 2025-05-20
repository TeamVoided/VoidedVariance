package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.minecraft.item.Items

object FabricEvents {
    fun init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register {
            it.addBottomIngredient(VVItems.TINTED_POTION)
            it.addBottomIngredient(VVItems.TINTED_SPLASH_POTION)
            it.addBottomIngredient(VVItems.TINTED_LINGERING_POTION)

            it.addItemRecipe(VVItems.TINTED_POTION, Items.GUNPOWDER, VVItems.TINTED_SPLASH_POTION)
            it.addItemRecipe(VVItems.TINTED_SPLASH_POTION, Items.DRAGON_BREATH, VVItems.TINTED_LINGERING_POTION)
        }
    }
}