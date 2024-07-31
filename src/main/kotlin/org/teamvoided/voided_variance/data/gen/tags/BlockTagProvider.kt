package org.teamvoided.voided_variance.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.BlockTags
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.addAll
import java.util.concurrent.CompletableFuture

class BlockTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(o, r) {
    override fun configure(wrapperLookup: HolderLookup.Provider) {
        vanillaTags()
        mineable()
    }

    private fun vanillaTags() {
        getOrCreateTagBuilder(BlockTags.FENCES)
            .add(VVBlocks.BRICK_FENCE)
    }

    private fun mineable() {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .addAll(VVBlocks.PICKAXABLE)
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .addAll(VVBlocks.AXABLE)
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
            .addAll(VVBlocks.SHOVELABLE)
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
            .addAll(VVBlocks.HOEABLE)
    }
}
