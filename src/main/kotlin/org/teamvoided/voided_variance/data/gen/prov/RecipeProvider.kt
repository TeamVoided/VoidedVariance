package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.item.Items
import net.minecraft.registry.HolderLookup
import org.teamvoided.voided_variance.block.VSlabBlock
import org.teamvoided.voided_variance.block.VStairsBlock
import org.teamvoided.voided_variance.block.VWallBlock
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.datagen.*
import java.util.concurrent.CompletableFuture

class RecipeProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricRecipeProvider(o, r) {
    override fun generateRecipes(e: RecipeExporter) {
        e.createFence(VVBlocks.BRICK_FENCE, Blocks.BRICKS, Items.BRICK, true)
        e.lantern(VVBlocks.REDSTONE_LANTERN, Items.REDSTONE_TORCH)

        STAIRS.forEach { e.createStair(it, (it as VStairsBlock).block, it != VVBlocks.SNOW_STAIR) }
        SLABS.forEach { e.createSlab(it, (it as VSlabBlock).block, true) }
        WALLS.forEach { e.createWall(it, (it as VWallBlock).block, it != VVBlocks.SNOW_WALL) }
    }
}
