package org.teamvoided.voided_variance.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import net.minecraft.tags.BlockTags.ENCHANTMENT_POWER_PROVIDER
import net.minecraft.world.level.block.Blocks
import org.teamvoided.voided_variance.data.tags.CBlockTags
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.BOOKSHELFS
import org.teamvoided.voided_variance.utils.addAll
import org.teamvoided.voided_variance.utils.datagen.*
import java.util.concurrent.CompletableFuture

class BlockTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : BlockTagProvider(o, r) {

    override fun addTags(lookup: HolderLookup.Provider) {
        vanillaTags()
        mineable()
        conventionalTags()
        sandstone()

        getOrCreateTagBuilder(ENCHANTMENT_POWER_PROVIDER).addAll(BOOKSHELFS)
    }

    private fun vanillaTags() {
        getOrCreateTagBuilder(BlockTags.FENCES).add(VVBlocks.BRICK_FENCE)

        getOrCreateTagBuilder(BlockTags.STAIRS).addAll(STAIRS)
        getOrCreateTagBuilder(BlockTags.SLABS).addAll(SLABS)
        getOrCreateTagBuilder(BlockTags.WALLS).addAll(WALLS)

        getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE)
            .add(VVBlocks.OBSIDIAN_STAIR, VVBlocks.OBSIDIAN_SLAB, VVBlocks.OBSIDIAN_WALL)
            .add(VVBlocks.CRYING_OBSIDIAN_STAIRS, VVBlocks.CRYING_OBSIDIAN_SLAB, VVBlocks.CRYING_OBSIDIAN_WALL)
            .add(VVBlocks.END_STONE_STAIR, VVBlocks.END_STONE_SLAB, VVBlocks.END_STONE_WALL)

        getOrCreateTagBuilder(BlockTags.SNOW).add(VVBlocks.SNOW_STAIR, VVBlocks.SNOW_SLAB, VVBlocks.SNOW_WALL)
    }

    private fun conventionalTags() {
        getOrCreateTagBuilder(ConventionalBlockTags.COBBLESTONES)
            .add(VVBlocks.INFESTED_MOSSY_COBBLESTONE, VVBlocks.INFESTED_COBBLED_DEEPSLATE)
    }

    private fun sandstone() {

        getOrCreateTagBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_BLOCKS)
            .add(VVBlocks.POLISHED_SANDSTONE, VVBlocks.ROUGH_SANDSTONE)
        getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_BLOCKS)
            .add(VVBlocks.POLISHED_RED_SANDSTONE, VVBlocks.ROUGH_RED_SANDSTONE)

        getOrCreateTagBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS)
            .add(VVBlocks.CUT_SANDSTONE_STAIR, VVBlocks.POLISHED_SANDSTONE_STAIRS, VVBlocks.ROUGH_SANDSTONE_STAIRS)
        getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_STAIRS)
            .add(
                VVBlocks.CUT_RED_SANDSTONE_STAIR, VVBlocks.POLISHED_RED_SANDSTONE_STAIRS,
                VVBlocks.ROUGH_RED_SANDSTONE_STAIRS
            )

        getOrCreateTagBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_SLABS)
            .add(VVBlocks.POLISHED_SANDSTONE_SLAB, VVBlocks.ROUGH_SANDSTONE_SLAB)
        getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_SLABS)
            .add(VVBlocks.POLISHED_RED_SANDSTONE_SLAB, VVBlocks.ROUGH_RED_SANDSTONE_SLAB)

        getOrCreateTagBuilder(CBlockTags.SANDSTONE_WALLS)
            .forceAddTag(CBlockTags.UNCOLORED_SANDSTONE_WALLS)
            .forceAddTag(CBlockTags.RED_SANDSTONE_WALLS)

        getOrCreateTagBuilder(CBlockTags.UNCOLORED_SANDSTONE_WALLS)
            .add(Blocks.SANDSTONE_WALL)
            .add(VVBlocks.CUT_SANDSTONE_WALL, VVBlocks.POLISHED_SANDSTONE_WALL, VVBlocks.ROUGH_SANDSTONE_WALL)
        getOrCreateTagBuilder(CBlockTags.RED_SANDSTONE_WALLS)
            .add(Blocks.RED_SANDSTONE_WALL)
            .add(
                VVBlocks.CUT_RED_SANDSTONE_WALL, VVBlocks.POLISHED_RED_SANDSTONE_WALL, VVBlocks.ROUGH_RED_SANDSTONE_WALL
            )
    }

    private fun mineable() {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
            .addAll(PICKAXABLE)
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
            .addAll(AXABLE)
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
            .addAll(SHOVELABLE)
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE)
            .addAll(HOEABLE)

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .addAll(NEEDS_STONE)
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
            .addAll(NEEDS_IRON)
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
            .addAll(NEEDS_DIAMOND)
    }
}
