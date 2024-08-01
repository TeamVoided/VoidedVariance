package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.InfestedBlock
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.datagen.fence
import org.teamvoided.voided_variance.utils.datagen.model
import org.teamvoided.voided_variance.utils.datagen.toVariant

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

    private val blockExclude = listOf(
        VVBlocks.BRICK_FENCE,
        VVBlocks.REDSTONE_LANTERN,
    )

    override fun generateBlockStateModels(gen: BlockStateModelGenerator) {
        blocks@ for (block in VVBlocks.BLOCKS) {
            if (blockExclude.contains(block)) continue@blocks
            when (block) {
                is InfestedBlock -> gen.registerInfested(block.regularBlock, block)
                else -> gen.registerSimpleCubeAll(block)
            }
        }

        gen.fence(VVBlocks.BRICK_FENCE, Blocks.BRICKS)
        gen.redstoneLantern(VVBlocks.REDSTONE_LANTERN)
    }

    override fun generateItemModels(gen: ItemModelGenerator) = Unit

    private fun BlockStateModelGenerator.redstoneLantern(block: Block) {
        this.registerItemModel(block.asItem())

        val lantern = TexturedModel.TEMPLATE_LANTERN.create(block, this.modelCollector)
        val lanternHanging = TexturedModel.TEMPLATE_HANGING_LANTERN.create(block, this.modelCollector)

        val litTex = Texture().put(TextureKey.LANTERN, block.model("_lit"))
        val litLantern = Models.TEMPLATE_LANTERN.upload(block.model("_lit"), litTex, this.modelCollector)
        val litLanternHanging = Models.TEMPLATE_HANGING_LANTERN.upload(block.model("_lit_hanging"), litTex, this.modelCollector)

        this.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(block).coordinate(
                BlockStateVariantMap.create(Properties.HANGING, Properties.LIT)
                    .register(false, false, lantern.toVariant())
                    .register(true, false, lanternHanging.toVariant())
                    .register(false, true, litLantern.toVariant())
                    .register(true, true, litLanternHanging.toVariant())
            )
        )
    }
}
