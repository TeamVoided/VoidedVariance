package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.InfestedBlock
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.BlockStateModelGenerator
import org.teamvoided.voided_variance.init.VVBlocks

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

    private val blockExclude = listOf<Block>()

    override fun generateBlockStateModels(gen: BlockStateModelGenerator) {
        blocks@ for (block in VVBlocks.BLOCKS) {
            if (blockExclude.contains(block)) continue@blocks
            when (block) {
                is InfestedBlock -> gen.registerInfested(block.regularBlock, block)
                else -> gen.registerSimpleCubeAll(block)
            }
        }
    }

    override fun generateItemModels(gen: ItemModelGenerator) = Unit
}