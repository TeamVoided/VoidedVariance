package org.teamvoided.voided_variance.utils

import net.minecraft.data.server.RecipesProvider.*
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.RecipeCategory


fun RecipeExporter.createFence(output: ItemConvertible, block: ItemConvertible) {
    createFence(output, block, block)
}

fun RecipeExporter.createFence(
    fence: ItemConvertible, block: ItemConvertible, item: ItemConvertible,
    stone: Boolean = false
) {
    ShapedRecipeJsonFactory.create(RecipeCategory.BUILDING_BLOCKS, fence, if (stone) 6 else 3)
        .pattern("#-#")
        .pattern("#-#")
        .ingredient('#', block)
        .ingredient('-', item)
        .criterion(hasItem(item), conditionsFromItem(item))
        .offerTo(this)
    if (stone) createStonecuttingRecipe(this, RecipeCategory.BUILDING_BLOCKS, fence, block, 1)
}