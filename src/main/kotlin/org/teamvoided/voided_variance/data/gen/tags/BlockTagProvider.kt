package org.teamvoided.voided_variance.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.BlockTags
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.addAll
import org.teamvoided.voided_variance.utils.datagen.*
import java.util.concurrent.CompletableFuture

class BlockTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(o, r) {
    override fun configure(wrapperLookup: HolderLookup.Provider) {
        vanillaTags()
        mineable()
        conventionalTags()
    }

    private fun vanillaTags() {
        getOrCreateTagBuilder(BlockTags.FENCES).add(VVBlocks.BRICK_FENCE)

        getOrCreateTagBuilder(BlockTags.STAIRS).addAll(STAIRS)
        getOrCreateTagBuilder(BlockTags.SLABS).addAll(SLABS)
        getOrCreateTagBuilder(BlockTags.WALLS).addAll(WALLS)

        getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE)
            .add(VVBlocks.OBSIDIAN_STAIR, VVBlocks.OBSIDIAN_SLAB, VVBlocks.OBSIDIAN_WALL)
            .add(VVBlocks.END_STONE_STAIR, VVBlocks.END_STONE_SLAB, VVBlocks.END_STONE_WALL)

        getOrCreateTagBuilder(BlockTags.SNOW).add(VVBlocks.SNOW_STAIR, VVBlocks.SNOW_WALL)
    }

    private fun conventionalTags() {
        getOrCreateTagBuilder(ConventionalBlockTags.COBBLESTONES)
            .add(VVBlocks.INFESTED_MOSSY_COBBLESTONE, VVBlocks.INFESTED_COBBLED_DEEPSLATE)

        getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_STAIRS)
            .add(VVBlocks.CUT_SANDSTONE_STAIR, VVBlocks.CUT_RED_SANDSTONE_STAIR)
        getOrCreateTagBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS).add(VVBlocks.CUT_SANDSTONE_STAIR)
        getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_STAIRS).add(VVBlocks.CUT_RED_SANDSTONE_STAIR)
    }

    private fun mineable() {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .addAll(PICKAXABLE)
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .addAll(AXABLE)
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
            .addAll(SHOVELABLE)
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
            .addAll(HOEABLE)

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .addAll(NEEDS_STONE)
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
            .addAll(NEEDS_IRON)
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
            .addAll(NEEDS_DIAMOND)
    }
}
