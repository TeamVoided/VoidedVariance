package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.InfestedBlock
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Property
import net.minecraft.util.Identifier
import org.teamvoided.voided_variance.VoidedVariance.mc
import org.teamvoided.voided_variance.block.CompositeBlock
import org.teamvoided.voided_variance.block.VSlabBlock
import org.teamvoided.voided_variance.block.VStairsBlock
import org.teamvoided.voided_variance.block.VWallBlock
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.BOOKSHELFS
import org.teamvoided.voided_variance.utils.datagen.*

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    private val blockExclude = setOf(
        VVBlocks.BRICK_FENCE,
        VVBlocks.REDSTONE_LANTERN,

        VVBlocks.SNOW_STAIR,
        VVBlocks.CUT_SANDSTONE_STAIR,
        VVBlocks.CUT_RED_SANDSTONE_STAIR,

        VVBlocks.SNOW_SLAB,

        VVBlocks.SNOW_WALL,
        VVBlocks.SMOOTH_SANDSTONE_WALL,
        VVBlocks.SMOOTH_RED_SANDSTONE_WALL,
        VVBlocks.SMOOTH_QUARTZ_WALL,
        VVBlocks.QUARTZ_WALL,

        VVBlocks.HEAVY_CUBE
    ) + BOOKSHELFS

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

        gen.slab(VVBlocks.SNOW_SLAB, Blocks.SNOW, Blocks.SNOW_BLOCK)

        listOf(
            Pair(VVBlocks.SNOW_WALL, Blocks.SNOW.model()),
            Pair(VVBlocks.SMOOTH_SANDSTONE_WALL, mc("block/sandstone_top")),
            Pair(VVBlocks.SMOOTH_RED_SANDSTONE_WALL, mc("block/red_sandstone_top")),
            Pair(VVBlocks.SMOOTH_QUARTZ_WALL, mc("block/quartz_block_bottom")),
            Pair(VVBlocks.QUARTZ_WALL, mc("block/quartz_block_side"))
        ).forEach { gen.wall(it.first, it.second) }

        gen.addAxis(Blocks.MANGROVE_ROOTS)
        gen.denseCube(VVBlocks.HEAVY_CUBE)

        gen.bookshelf(VVBlocks.SPRUCE_BOOKSHELF, Blocks.SPRUCE_PLANKS)
        gen.bookshelf(VVBlocks.BIRCH_BOOKSHELF, Blocks.BIRCH_PLANKS)
        gen.bookshelf(VVBlocks.JUNGLE_BOOKSHELF, Blocks.JUNGLE_PLANKS)
        gen.bookshelf(VVBlocks.ACACIA_BOOKSHELF, Blocks.ACACIA_PLANKS)
        gen.bookshelf(VVBlocks.DARK_OAK_BOOKSHELF, Blocks.DARK_OAK_PLANKS)
        gen.bookshelf(VVBlocks.MANGROVE_BOOKSHELF, Blocks.MANGROVE_PLANKS)
        gen.bookshelf(VVBlocks.CHERRY_BOOKSHELF, Blocks.CHERRY_PLANKS)
        gen.bookshelf(VVBlocks.BAMBOO_BOOKSHELF, Blocks.BAMBOO_PLANKS)
        gen.bookshelf(VVBlocks.CRIMSON_BOOKSHELF, Blocks.CRIMSON_PLANKS)
        gen.bookshelf(VVBlocks.WARPED_BOOKSHELF, Blocks.WARPED_PLANKS)
    }

    override fun generateItemModels(gen: ItemModelGenerator) {
        gen.register(VVItems.TINTED_GLASS_BOTTLE, Models.SINGLE_LAYER_ITEM)
    }

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

    private fun BlockStateModelGenerator.addAxis(block: Block) = this.blockStateCollector.accept(
        BlockStateModelGenerator.createAxisRotatedBlockState(block, ModelIds.getBlockModelId(block))
    )

    private fun BlockStateModelGenerator.bookshelf(bookshelf: Block, top: Block) {
        val texture = Texture.sideEnd(Texture.getId(bookshelf), Texture.getId(top))
        val model = Models.CUBE_COLUMN.upload(bookshelf, texture, this.modelCollector)
        this.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(bookshelf, model))
    }

    private fun BlockStateModelGenerator.denseCube(block: Block) {
        val topModel = ModelIds.getBlockSubModelId(block, "_top")
        val bottomModel = ModelIds.getBlockSubModelId(block, "_bottom")
        val itemModel = TexturedModel.CUBE_BOTTOM_TOP.create(block, this.modelCollector)
        this.registerParentedItemModel(block.asItem(), itemModel)
        this.blockStateCollector.accept(
            MultipartBlockStateSupplier.create(block)
                .with(CompositeBlock.UPPER_NORTH_EAST, true, variant(topModel, VariantSettings.Rotation.R90))
                .with(CompositeBlock.UPPER_NORTH_WEST, true, variant(topModel))
                .with(CompositeBlock.UPPER_SOUTH_EAST, true, variant(topModel, VariantSettings.Rotation.R180))
                .with(CompositeBlock.UPPER_SOUTH_WEST, true, variant(topModel, VariantSettings.Rotation.R270))
                .with(CompositeBlock.LOWER_NORTH_EAST, true, variant(bottomModel, VariantSettings.Rotation.R90))
                .with(CompositeBlock.LOWER_NORTH_WEST, true, variant(bottomModel))
                .with(CompositeBlock.LOWER_SOUTH_EAST, true, variant(bottomModel, VariantSettings.Rotation.R180))
                .with(CompositeBlock.LOWER_SOUTH_WEST, true, variant(bottomModel, VariantSettings.Rotation.R270))
        )
    }

    fun <T : Comparable<T>> MultipartBlockStateSupplier.with(
        property: Property<T>, value: T, vararg variants: BlockStateVariant,
    ) = this.with(When.create().set(property, value), *variants)

    fun variant(model: Identifier) = BlockStateVariant().put(VariantSettings.MODEL, model)
    fun variant(model: Identifier, rotation: VariantSettings.Rotation) =
        variant(model).put(VariantSettings.Y, rotation).put(VariantSettings.UVLOCK, true)

}
