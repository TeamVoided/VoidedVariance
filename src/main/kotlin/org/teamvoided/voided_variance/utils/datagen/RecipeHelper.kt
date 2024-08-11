package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.server.RecipesProvider.*
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeJsonFactory
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.RecipeCategory
import net.minecraft.registry.tag.TagKey


fun RecipeExporter.createFence(
    fence: ItemConvertible, block: ItemConvertible, item: ItemConvertible,
    stone: Boolean = false
) {
    ShapedRecipeJsonFactory.create(RecipeCategory.BUILDING_BLOCKS, fence, if (stone) 6 else 3)
        .pattern("#-#")
        .pattern("#-#")
        .ingredient('#', block)
        .ingredient('-', item)
        .criterion(item)
        .offerTo(this)
    if (stone) createStonecuttingRecipe(this, RecipeCategory.BUILDING_BLOCKS, fence, block, 1)
}

fun RecipeExporter.createSlab(
    slab: ItemConvertible, block: ItemConvertible,
    stone: Boolean = false
) {
    ShapedRecipeJsonFactory.create(RecipeCategory.BUILDING_BLOCKS, slab, 6)
        .pattern("###")
        .ingredient('#', block)
        .criterion(block)
        .offerTo(this)
    if (stone) createStonecuttingRecipe(this, RecipeCategory.BUILDING_BLOCKS, slab, block, 2)
}

fun RecipeExporter.createStair(
    stair: ItemConvertible, block: ItemConvertible,
    stone: Boolean = false
) {
    ShapedRecipeJsonFactory.create(RecipeCategory.BUILDING_BLOCKS, stair, 4)
        .pattern("#  ")
        .pattern("## ")
        .pattern("###")
        .ingredient('#', block)
        .criterion(block)
        .offerTo(this)
    if (stone) createStonecuttingRecipe(this, RecipeCategory.BUILDING_BLOCKS, stair, block, 1)
}
fun RecipeExporter.createWall(
    wall: ItemConvertible, block: ItemConvertible,
    stone: Boolean = false
) {
    ShapedRecipeJsonFactory.create(RecipeCategory.BUILDING_BLOCKS, wall, 6)
        .pattern("###")
        .pattern("###")
        .ingredient('#', block)
        .criterion(block)
        .offerTo(this)
    if (stone) createStonecuttingRecipe(this, RecipeCategory.BUILDING_BLOCKS, wall, block, 1)
}

fun RecipeExporter.lantern(lantern: ItemConvertible, torch: ItemConvertible) {
    ShapedRecipeJsonFactory.create(RecipeCategory.DECORATIONS, lantern)
        .pattern("XXX")
        .pattern("X#X")
        .pattern("XXX")
        .ingredient('#', torch)
        .ingredient('X', Items.IRON_NUGGET)
        .criterion(Items.IRON_NUGGET)
        .criterion(Items.IRON_INGOT)
        .offerTo(this)
}


fun RecipeJsonFactory.criterion(item: ItemConvertible): RecipeJsonFactory =
    this.criterion(hasItem(item), conditionsFromItem(item))

fun RecipeJsonFactory.criterion(tag: TagKey<Item>): RecipeJsonFactory =
    this.criterion("has_${tag.id.path}", conditionsFromTag(tag))