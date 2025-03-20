package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.registry.HolderLookup
import org.teamvoided.voided_variance.block.CompositeBlock
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVBlocks.HEAVY_CUBE
import java.util.concurrent.CompletableFuture

class BlockLootTableProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(o, r) {
    private val excludeList = listOf(HEAVY_CUBE)

    override fun generate() {
        VVBlocks.BLOCKS.filter { it !in excludeList }.forEach {
            when (it) {
                is SlabBlock -> add(it, ::slabDrops)
                else -> addDrop(it)
            }
        }

        add(HEAVY_CUBE) { dropCompositeBlock(it, Items.HEAVY_CORE) }
    }

    fun dropCompositeBlock(block: Block, partItem: Item): LootTable.Builder {
        val fullProperties = StatePredicate.Builder.create()
        val partItemEntry = ItemEntry.builder(partItem)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(0f), false))
        CompositeBlock.PROPS.forEach {
            fullProperties.exactMatch(it, true)
            partItemEntry.apply(
                SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f), true).conditionally(
                    BlockStatePropertyLootCondition.builder(block)
                        .properties(StatePredicate.Builder.create().exactMatch(it, true))
                )
            )
        }
        return LootTable.builder().pool(
            LootPool.builder()
                .with(
                    applyExplosionDecay(
                        block, ItemEntry.builder(block)
                            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(fullProperties))
                    ).alternatively(partItemEntry)
                )
        )
    }
}

