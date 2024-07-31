package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.item.Items
import net.minecraft.registry.HolderLookup
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.createFence
import java.util.concurrent.CompletableFuture

class RecipeProvider (o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricRecipeProvider(o, r) {
    override fun generateRecipes(e: RecipeExporter) {
         e.createFence(VVBlocks.BRICK_FENCE, Blocks.BRICKS, Items.BRICK, true)
    }
}
