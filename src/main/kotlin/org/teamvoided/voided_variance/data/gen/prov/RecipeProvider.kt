package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.block.Blocks
import net.minecraft.block.Blocks.BOOKSHELF
import net.minecraft.data.server.recipe.CookingRecipeJsonFactory
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeJsonFactory
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeCategory
import net.minecraft.registry.HolderLookup
import net.minecraft.util.Identifier
import org.teamvoided.voided_variance.VoidedVariance.mc
import org.teamvoided.voided_variance.block.VSlabBlock
import org.teamvoided.voided_variance.block.VStairsBlock
import org.teamvoided.voided_variance.block.VWallBlock
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.datagen.*
import java.util.concurrent.CompletableFuture

class RecipeProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricRecipeProvider(o, r) {
    override fun generateRecipes(e: RecipeExporter) {
        e.createFence(VVBlocks.BRICK_FENCE, Blocks.BRICKS, Items.BRICK, true)
        e.lantern(VVBlocks.REDSTONE_LANTERN, Items.REDSTONE_TORCH)

        STAIRS.forEach { e.createStair(it, (it as VStairsBlock).block, it != VVBlocks.SNOW_STAIR) }
        SLABS.filter { it != VVBlocks.SNOW_SLAB }.forEach { e.createSlab(it, (it as VSlabBlock).block, true) }
        WALLS.forEach { e.createWall(it, (it as VWallBlock).block, it != VVBlocks.SNOW_WALL) }

        e.compositeBlock(VVBlocks.HEAVY_CUBE, Items.HEAVY_CORE)
        e.sandstone()
        e.bookshelf()

        CookingRecipeJsonFactory.createSmelting(
            Ingredient.ofItems(Blocks.LAPIS_BLOCK), RecipeCategory.BUILDING_BLOCKS, VVBlocks.SMOOTH_LAPIS, 0.1f, 200
        ).criterion("has_lapis_block", conditionsFromItem(Blocks.LAPIS_BLOCK)).offerTo(e)

        ShapedRecipeJsonFactory.create(RecipeCategory.BREWING, VVItems.TINTED_GLASS_BOTTLE, 3)
            .pattern("# #")
            .pattern(" # ")
            .ingredient('#', Blocks.TINTED_GLASS)
            .criterion(Blocks.TINTED_GLASS)
            .offerTo(e)
    }

    fun RecipeExporter.sandstone() {
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

    fun RecipeExporter.bookshelf() {
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

    fun RecipeExporter.bookshelf(
        bookshelf: ItemConvertible, planks: ItemConvertible, id: Identifier = RecipeJsonFactory.getItemId(bookshelf),
    ) {
        ShapedRecipeJsonFactory.create(RecipeCategory.BUILDING_BLOCKS, bookshelf)
            .pattern("###")
            .pattern("XXX")
            .pattern("###")
            .ingredient('#', planks)
            .ingredient('X', Items.BOOK)
            .criterion(Items.BOOK)
            .offerTo(this, id)
    }
}
