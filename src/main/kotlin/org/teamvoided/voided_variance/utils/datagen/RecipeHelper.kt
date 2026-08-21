package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider.getHasName
import net.minecraft.data.recipes.RecipeProvider.has
import net.minecraft.data.recipes.RecipeProvider.stonecutterResultFromBase
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike


fun RecipeOutput.compositeBlock(full: ItemLike, part: ItemLike) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, full, 1)
        .pattern("###")
        .pattern("# #")
        .pattern("###")
        .define('#', part)
        .unlockedBy(part)
        .save(this)
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, part, 8)
        .requires(full)
        .unlockedBy(full)
        .save(this)
}


fun RecipeOutput.stonecutAllFrom(input: ItemLike, vararg results: ItemLike) {
    for (result in results) stonecutterResultFromBase(this, RecipeCategory.BUILDING_BLOCKS, result, input, 1)
}

fun RecipeOutput.stonecutAllFrom(inputs: List<ItemLike>, vararg results: ItemLike) {
    for (input in inputs) this.stonecutAllFrom(input, *results)
}

fun RecipeOutput.stonecutting(output: ItemLike, input: ItemLike, count: Int) =
    stonecutterResultFromBase(this, RecipeCategory.BUILDING_BLOCKS, output, input, count)

fun RecipeOutput.stonecutting(output: ItemLike, count: Int, vararg inputs: ItemLike) {
    for (input in inputs) this.stonecutting(output, input, count)
}

fun RecipeOutput.create2x2(output: ItemLike, input: ItemLike, count: Int = 4) =
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, count)
        .pattern("##")
        .pattern("##")
        .define('#', input)
        .unlockedBy(input)
        .save(this)


fun RecipeOutput.createFence(
    fence: ItemLike, block: ItemLike, item: ItemLike,
    stone: Boolean = false
) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fence, if (stone) 6 else 3)
        .pattern("#-#")
        .pattern("#-#")
        .define('#', block)
        .define('-', item)
        .unlockedBy(item)
        .save(this)
    if (stone) stonecutterResultFromBase(this, RecipeCategory.BUILDING_BLOCKS, fence, block, 1)
}

fun RecipeOutput.createSlab(
    slab: ItemLike, block: ItemLike,
    stone: Boolean = false
) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
        .pattern("###")
        .define('#', block)
        .unlockedBy(block)
        .save(this)
    if (stone) stonecutterResultFromBase(this, RecipeCategory.BUILDING_BLOCKS, slab, block, 2)
}

fun RecipeOutput.createStair(
    stair: ItemLike, block: ItemLike,
    stone: Boolean = false
) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair, 4)
        .pattern("#  ")
        .pattern("## ")
        .pattern("###")
        .define('#', block)
        .unlockedBy(block)
        .save(this)
    if (stone) stonecutterResultFromBase(this, RecipeCategory.BUILDING_BLOCKS, stair, block, 1)
}

fun RecipeOutput.createWall(
    wall: ItemLike, block: ItemLike,
    stone: Boolean = false
) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
        .pattern("###")
        .pattern("###")
        .define('#', block)
        .unlockedBy(block)
        .save(this)
    if (stone) stonecutterResultFromBase(this, RecipeCategory.BUILDING_BLOCKS, wall, block, 1)
}

fun RecipeOutput.lantern(lantern: ItemLike, torch: ItemLike) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, lantern)
        .pattern("XXX")
        .pattern("X#X")
        .pattern("XXX")
        .define('#', torch)
        .define('X', Items.IRON_NUGGET)
        .unlockedBy(Items.IRON_NUGGET)
        .unlockedBy(Items.IRON_INGOT)
        .save(this)
}


fun RecipeBuilder.unlockedBy(item: ItemLike): RecipeBuilder = unlockedBy(getHasName(item), has(item))
fun RecipeBuilder.unlockedBy(tag: TagKey<Item>): RecipeBuilder = unlockedBy("has_${tag.location.path}", has(tag))