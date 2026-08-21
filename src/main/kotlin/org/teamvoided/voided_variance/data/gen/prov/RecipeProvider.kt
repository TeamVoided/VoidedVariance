package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.*
import net.minecraft.data.recipes.RecipeBuilder.getDefaultRecipeId
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.Blocks.BOOKSHELF
import org.teamvoided.voided_variance.VoidedVariance.mc
import org.teamvoided.voided_variance.block.VSlabBlock
import org.teamvoided.voided_variance.block.VStairsBlock
import org.teamvoided.voided_variance.block.VWallBlock
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.datagen.*
import java.util.concurrent.CompletableFuture

class RecipeProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricRecipeProvider(o, r) {

    override fun buildRecipes(output: RecipeOutput) {
        output.createFence(VVBlocks.BRICK_FENCE, Blocks.BRICKS, Items.BRICK, true)
        output.lantern(VVBlocks.REDSTONE_LANTERN, Items.REDSTONE_TORCH)

        STAIRS.forEach { output.createStair(it, (it as VStairsBlock).block, it != VVBlocks.SNOW_STAIR) }
        SLABS.filter { it != VVBlocks.SNOW_SLAB }.forEach { output.createSlab(it, (it as VSlabBlock).block, true) }
        WALLS.forEach { output.createWall(it, (it as VWallBlock).block, it != VVBlocks.SNOW_WALL) }

        output.compositeBlock(VVBlocks.HEAVY_CUBE, Items.HEAVY_CORE)
        output.sandstone()
        output.bookshelf()

        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(Blocks.LAPIS_BLOCK), RecipeCategory.BUILDING_BLOCKS, VVBlocks.SMOOTH_LAPIS, 0.1f, 200
        ).unlockedBy("has_lapis_block", has(Blocks.LAPIS_BLOCK)).save(output)

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, VVItems.TINTED_GLASS_BOTTLE, 3)
            .pattern("# #")
            .pattern(" # ")
            .define('#', Blocks.TINTED_GLASS)
            .unlockedBy(Blocks.TINTED_GLASS)
            .save(output)

        output.carpetPlate(VVBlocks.WHITE_CARPET_PLATE, Blocks.WHITE_CARPET)
        output.carpetPlate(VVBlocks.ORANGE_CARPET_PLATE, Blocks.ORANGE_CARPET)
        output.carpetPlate(VVBlocks.MAGENTA_CARPET_PLATE, Blocks.MAGENTA_CARPET)
        output.carpetPlate(VVBlocks.LIGHT_BLUE_CARPET_PLATE, Blocks.LIGHT_BLUE_CARPET)
        output.carpetPlate(VVBlocks.YELLOW_CARPET_PLATE, Blocks.YELLOW_CARPET)
        output.carpetPlate(VVBlocks.LIME_CARPET_PLATE, Blocks.LIME_CARPET)
        output.carpetPlate(VVBlocks.PINK_CARPET_PLATE, Blocks.PINK_CARPET)
        output.carpetPlate(VVBlocks.GRAY_CARPET_PLATE, Blocks.GRAY_CARPET)
        output.carpetPlate(VVBlocks.LIGHT_GRAY_CARPET_PLATE, Blocks.LIGHT_GRAY_CARPET)
        output.carpetPlate(VVBlocks.CYAN_CARPET_PLATE, Blocks.CYAN_CARPET)
        output.carpetPlate(VVBlocks.PURPLE_CARPET_PLATE, Blocks.PURPLE_CARPET)
        output.carpetPlate(VVBlocks.BLUE_CARPET_PLATE, Blocks.BLUE_CARPET)
        output.carpetPlate(VVBlocks.BROWN_CARPET_PLATE, Blocks.BROWN_CARPET)
        output.carpetPlate(VVBlocks.GREEN_CARPET_PLATE, Blocks.GREEN_CARPET)
        output.carpetPlate(VVBlocks.RED_CARPET_PLATE, Blocks.RED_CARPET)
        output.carpetPlate(VVBlocks.BLACK_CARPET_PLATE, Blocks.BLACK_CARPET)
        output.carpetPlate(VVBlocks.MOSS_CARPET_PLATE, Blocks.MOSS_CARPET)

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VVBlocks.TINTED_GLASS_PANE, 16)
            .define('#', Blocks.TINTED_GLASS)
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_glass", has(Blocks.TINTED_GLASS))
            .save(output)
    }

    fun RecipeOutput.sandstone() {
        // Polished Sandstone
        this.stonecutAllFrom(
            listOf(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE),
            VVBlocks.POLISHED_SANDSTONE, VVBlocks.POLISHED_SANDSTONE_STAIRS, VVBlocks.POLISHED_SANDSTONE_WALL
        )
        this.stonecutting(VVBlocks.POLISHED_SANDSTONE_SLAB, 2, Blocks.SANDSTONE, Blocks.CUT_SANDSTONE)
        this.create2x2(VVBlocks.POLISHED_SANDSTONE, Blocks.CUT_SANDSTONE)


        this.stonecutAllFrom(
            listOf(Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE),
            VVBlocks.POLISHED_RED_SANDSTONE,
            VVBlocks.POLISHED_RED_SANDSTONE_STAIRS, VVBlocks.POLISHED_RED_SANDSTONE_WALL
        )
        this.stonecutting(VVBlocks.POLISHED_RED_SANDSTONE_SLAB, 2, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)
        this.create2x2(VVBlocks.POLISHED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)

        // Rough Sandstone
        this.stonecutAllFrom(
            Blocks.SANDSTONE, VVBlocks.ROUGH_SANDSTONE, VVBlocks.ROUGH_SANDSTONE_STAIRS, VVBlocks.ROUGH_SANDSTONE_WALL
        )
        this.stonecutting(VVBlocks.ROUGH_SANDSTONE_SLAB, 2, Blocks.SANDSTONE)
        this.stonecutAllFrom(
            Blocks.RED_SANDSTONE,
            VVBlocks.ROUGH_RED_SANDSTONE,
            VVBlocks.ROUGH_RED_SANDSTONE_STAIRS,
            VVBlocks.ROUGH_RED_SANDSTONE_WALL
        )
        this.stonecutting(VVBlocks.ROUGH_RED_SANDSTONE_SLAB, 2, Blocks.RED_SANDSTONE)
    }

    fun RecipeOutput.bookshelf() {
        this.bookshelf(BOOKSHELF, Blocks.OAK_PLANKS, mc("oak_bookshelf"))
        this.bookshelf(VVBlocks.SPRUCE_BOOKSHELF, Blocks.SPRUCE_PLANKS)
        this.bookshelf(VVBlocks.BIRCH_BOOKSHELF, Blocks.BIRCH_PLANKS)
        this.bookshelf(VVBlocks.JUNGLE_BOOKSHELF, Blocks.JUNGLE_PLANKS)
        this.bookshelf(VVBlocks.ACACIA_BOOKSHELF, Blocks.ACACIA_PLANKS)
        this.bookshelf(VVBlocks.DARK_OAK_BOOKSHELF, Blocks.DARK_OAK_PLANKS)
        this.bookshelf(VVBlocks.MANGROVE_BOOKSHELF, Blocks.MANGROVE_PLANKS)
        this.bookshelf(VVBlocks.CHERRY_BOOKSHELF, Blocks.CHERRY_PLANKS)
        this.bookshelf(VVBlocks.BAMBOO_BOOKSHELF, Blocks.BAMBOO_PLANKS)
        this.bookshelf(VVBlocks.CRIMSON_BOOKSHELF, Blocks.CRIMSON_PLANKS)
        this.bookshelf(VVBlocks.WARPED_BOOKSHELF, Blocks.WARPED_PLANKS)
    }

    fun RecipeOutput.bookshelf(
        bookshelf: ItemLike, planks: ItemLike, id: ResourceLocation = getDefaultRecipeId(bookshelf),
    ) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf)
            .pattern("###")
            .pattern("XXX")
            .pattern("###")
            .define('#', planks)
            .define('X', Items.BOOK)
            .unlockedBy(Items.BOOK)
            .save(this, id)
    }

    fun RecipeOutput.carpetPlate(plate: ItemLike, carpet: ItemLike) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plate)
            .requires(ItemTags.WOODEN_PRESSURE_PLATES)
            .requires(carpet)
            .unlockedBy(ItemTags.WOODEN_PRESSURE_PLATES)
            .save(this)
    }

}