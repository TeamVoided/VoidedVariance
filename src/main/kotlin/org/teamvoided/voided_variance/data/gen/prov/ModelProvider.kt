package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.InfestedBlock
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import org.teamvoided.voided_variance.VoidedVariance.mc
import org.teamvoided.voided_variance.block.VSlabBlock
import org.teamvoided.voided_variance.block.VStairsBlock
import org.teamvoided.voided_variance.block.VWallBlock
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.utils.datagen.*

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    private val blockExclude = listOf(
        VVBlocks.BRICK_FENCE,
        VVBlocks.REDSTONE_LANTERN,

        VVBlocks.SNOW_STAIR,
        VVBlocks.CUT_SANDSTONE_STAIR,
        VVBlocks.CUT_RED_SANDSTONE_STAIR,

        VVBlocks.SNOW_WALL,
        VVBlocks.SMOOTH_SANDSTONE_WALL,
        VVBlocks.SMOOTH_RED_SANDSTONE_WALL,
        VVBlocks.SMOOTH_QUARTZ_WALL,
        VVBlocks.QUARTZ_WALL,

        VVBlocks.SNOW_WALL,
        VVBlocks.SMOOTH_SANDSTONE_WALL,
        VVBlocks.SMOOTH_RED_SANDSTONE_WALL,
        VVBlocks.SMOOTH_QUARTZ_WALL,
        VVBlocks.QUARTZ_WALL,
    )

    override fun generateBlockStateModels(gen: BlockStateModelGenerator) {
        blocks@ for (block in VVBlocks.BLOCKS) {
            if (blockExclude.contains(block)) continue@blocks
            when (block) {
                is InfestedBlock -> gen.registerInfested(block.regularBlock, block)
                is VWallBlock -> {
                    if (block == VVBlocks.PURPUR_WALL) gen.wallOffset(block, block.block)
                    else gen.wall(block, block.block)
                }

                is VSlabBlock -> gen.slab(block, block.block)
                is VStairsBlock -> gen.stairs(block, block.block)
                else -> gen.registerSimpleCubeAll(block)
            }
        }

        gen.fence(VVBlocks.BRICK_FENCE, Blocks.BRICKS)
        gen.redstoneLantern(VVBlocks.REDSTONE_LANTERN)

        gen.stairs(VVBlocks.SNOW_STAIR, Blocks.SNOW)
        gen.stairs(VVBlocks.CUT_SANDSTONE_STAIR, mc("block/sandstone_top"), Blocks.CUT_SANDSTONE.model())
        gen.stairs(VVBlocks.CUT_RED_SANDSTONE_STAIR, mc("block/red_sandstone_top"), Blocks.CUT_RED_SANDSTONE.model())

        listOf(
            Pair(VVBlocks.SNOW_WALL, Blocks.SNOW.model()),
            Pair(VVBlocks.SMOOTH_SANDSTONE_WALL, mc("block/sandstone_top")),
            Pair(VVBlocks.SMOOTH_RED_SANDSTONE_WALL, mc("block/red_sandstone_top")),
            Pair(VVBlocks.SMOOTH_QUARTZ_WALL, mc("block/quartz_block_bottom")),
            Pair(VVBlocks.QUARTZ_WALL, mc("block/quartz_block_side"))
        ).forEach { gen.wall(it.first, it.second) }
    }

    override fun generateItemModels(gen: ItemModelGenerator) = Unit

    private fun BlockStateModelGenerator.redstoneLantern(block: Block) {
        this.registerItemModel(block.asItem())

        val lantern = TexturedModel.TEMPLATE_LANTERN.create(block, this.modelCollector)
        val lanternHanging = TexturedModel.TEMPLATE_HANGING_LANTERN.create(block, this.modelCollector)

        val litTex = Texture().put(TextureKey.LANTERN, block.model("_lit"))
        val litLantern = Models.TEMPLATE_LANTERN.upload(block.model("_lit"), litTex, this.modelCollector)
        val litLanternHanging =
            Models.TEMPLATE_HANGING_LANTERN.upload(block.model("_lit_hanging"), litTex, this.modelCollector)

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
