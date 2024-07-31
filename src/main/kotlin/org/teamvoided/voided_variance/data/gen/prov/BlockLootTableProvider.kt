package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.registry.HolderLookup
import org.teamvoided.voided_variance.init.VVBlocks
import java.util.concurrent.CompletableFuture

class BlockLootTableProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(o, r) {
    private val excludeList = listOf<Block>()

    override fun generate() {
        VVBlocks.BLOCKS.filter { it !in excludeList }.forEach {
            when (it) {
                is SlabBlock -> add(it, ::slabDrops)
                else -> addDrop(it)
            }
        }
    }
}
