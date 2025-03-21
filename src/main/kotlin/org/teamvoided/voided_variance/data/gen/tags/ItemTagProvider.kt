package org.teamvoided.voided_variance.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.ItemTags
import org.teamvoided.voided_variance.data.tags.CBlockTags
import org.teamvoided.voided_variance.data.tags.CItemTags
import java.util.concurrent.CompletableFuture

class ItemTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>, blockTags: BlockTagProvider) :
    FabricTagProvider.ItemTagProvider(o, r, blockTags) {

    override fun configure(wrapperLookup: HolderLookup.Provider) {
        copyVanillaTags()
        initConventionalTags()
        copyConventionalTags()
    }

    private fun copyVanillaTags() {
        copy(BlockTags.FENCES, ItemTags.FENCES)

        copy(BlockTags.STAIRS, ItemTags.STAIRS)
        copy(BlockTags.SLABS, ItemTags.SLABS)
        copy(BlockTags.WALLS, ItemTags.WALLS)
    }

    private fun initConventionalTags() {
        getOrCreateTagBuilder(CItemTags.SANDSTONE_WALLS)
            .forceAddTag(CItemTags.UNCOLORED_SANDSTONE_WALLS)
            .forceAddTag(CItemTags.RED_SANDSTONE_WALLS)
    }

    private fun copyConventionalTags() {
        copy(ConventionalBlockTags.COBBLESTONES, ConventionalItemTags.COBBLESTONES)

        // Sandstone
        copy(ConventionalBlockTags.UNCOLORED_SANDSTONE_BLOCKS, ConventionalItemTags.UNCOLORED_SANDSTONE_BLOCKS)
        copy(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS, ConventionalItemTags.UNCOLORED_SANDSTONE_STAIRS)
        copy(ConventionalBlockTags.UNCOLORED_SANDSTONE_SLABS, ConventionalItemTags.UNCOLORED_SANDSTONE_SLABS)
        copy(CBlockTags.UNCOLORED_SANDSTONE_WALLS, CItemTags.UNCOLORED_SANDSTONE_WALLS)
        copy(ConventionalBlockTags.RED_SANDSTONE_BLOCKS, ConventionalItemTags.RED_SANDSTONE_BLOCKS)
        copy(ConventionalBlockTags.RED_SANDSTONE_STAIRS, ConventionalItemTags.RED_SANDSTONE_STAIRS)
        copy(ConventionalBlockTags.RED_SANDSTONE_SLABS, ConventionalItemTags.RED_SANDSTONE_SLABS)
        copy(CBlockTags.RED_SANDSTONE_WALLS, CItemTags.RED_SANDSTONE_WALLS)
    }

}
