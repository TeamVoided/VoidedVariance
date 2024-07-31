package org.teamvoided.voided_variance.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>, blockTags: BlockTagProvider) :
    FabricTagProvider.ItemTagProvider(o, r, blockTags) {

    override fun configure(wrapperLookup: HolderLookup.Provider) {
        copyVanillaTags()
    }

    private fun copyVanillaTags() {
        copy(BlockTags.FENCES, ItemTags.FENCES)
    }
}