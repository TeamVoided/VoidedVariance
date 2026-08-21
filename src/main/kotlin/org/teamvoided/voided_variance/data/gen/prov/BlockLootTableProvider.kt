package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
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
                is SlabBlock -> add(it, ::createSlabItemTable)
                else -> dropSelf(it)
            }
        }

        add(HEAVY_CUBE) { dropCompositeBlock(it, Items.HEAVY_CORE) }
    }

    fun dropCompositeBlock(block: Block, partItem: Item): LootTable.Builder {
        val fullProperties = StatePropertiesPredicate.Builder.properties()
        val partItemEntry = LootItem.lootTableItem(partItem)
            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(0f), false))
        CompositeBlock.PROPS.forEach {
            fullProperties.hasProperty(it, true)
            partItemEntry.apply(
                SetItemCountFunction.setCount(ConstantValue.exactly(1f), true).`when`(
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(it, true))
                )
            )
        }
        return LootTable.lootTable().pool(
            LootPool.lootPool().add(
                applyExplosionDecay(
                    block, LootItem.lootTableItem(block).`when`(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(fullProperties)
                    )
                ).otherwise(partItemEntry)
            ).build()
        )
    }
}

