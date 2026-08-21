package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.BlockModelGenerators.createSimpleBlock
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.blockstates.*
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.data.models.model.TexturedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.InfestedBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property
import org.teamvoided.voided_variance.VoidedVariance.mc
import org.teamvoided.voided_variance.block.*
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

        VVBlocks.HEAVY_CUBE,
        VVBlocks.TINTED_GLASS_PANE
    ) + BOOKSHELFS

    override fun generateBlockStateModels(gen: BlockModelGenerators) {
        for (block in VVBlocks.BLOCKS) {
            if (blockExclude.contains(block)) continue
            when (block) {
                is CarpetPlateBlock -> continue
                is InfestedBlock -> gen.copyModel(block.hostBlock, block)
                is VWallBlock -> {
                    if (block == VVBlocks.PURPUR_WALL) gen.wallOffset(block, block.block)
                    else gen.wall(block, block.block)
                }

                is VSlabBlock -> gen.slab(block, block.block)
                is VStairsBlock -> gen.stairs(block, block.block)
                else -> gen.createTrivialCube(block)
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


        gen.carpetPlate(VVBlocks.WHITE_CARPET_PLATE, Blocks.WHITE_WOOL)
        gen.carpetPlate(VVBlocks.ORANGE_CARPET_PLATE, Blocks.ORANGE_WOOL)
        gen.carpetPlate(VVBlocks.MAGENTA_CARPET_PLATE, Blocks.MAGENTA_WOOL)
        gen.carpetPlate(VVBlocks.LIGHT_BLUE_CARPET_PLATE, Blocks.LIGHT_BLUE_WOOL)
        gen.carpetPlate(VVBlocks.YELLOW_CARPET_PLATE, Blocks.YELLOW_WOOL)
        gen.carpetPlate(VVBlocks.LIME_CARPET_PLATE, Blocks.LIME_WOOL)
        gen.carpetPlate(VVBlocks.PINK_CARPET_PLATE, Blocks.PINK_WOOL)
        gen.carpetPlate(VVBlocks.GRAY_CARPET_PLATE, Blocks.GRAY_WOOL)
        gen.carpetPlate(VVBlocks.LIGHT_GRAY_CARPET_PLATE, Blocks.LIGHT_GRAY_WOOL)
        gen.carpetPlate(VVBlocks.CYAN_CARPET_PLATE, Blocks.CYAN_WOOL)
        gen.carpetPlate(VVBlocks.PURPLE_CARPET_PLATE, Blocks.PURPLE_WOOL)
        gen.carpetPlate(VVBlocks.BLUE_CARPET_PLATE, Blocks.BLUE_WOOL)
        gen.carpetPlate(VVBlocks.BROWN_CARPET_PLATE, Blocks.BROWN_WOOL)
        gen.carpetPlate(VVBlocks.GREEN_CARPET_PLATE, Blocks.GREEN_WOOL)
        gen.carpetPlate(VVBlocks.RED_CARPET_PLATE, Blocks.RED_WOOL)
        gen.carpetPlate(VVBlocks.BLACK_CARPET_PLATE, Blocks.BLACK_WOOL)

        gen.carpetPlate(VVBlocks.MOSS_CARPET_PLATE, Blocks.MOSS_BLOCK)

        gen.tintedPane(Blocks.TINTED_GLASS, VVBlocks.TINTED_GLASS_PANE)
    }

    override fun generateItemModels(gen: ItemModelGenerators) {
        gen.generateFlatItem(VVItems.TINTED_GLASS_BOTTLE, ModelTemplates.FLAT_ITEM)
    }

    private fun BlockModelGenerators.redstoneLantern(block: Block) {
        createSimpleFlatItemModel(block.asItem())

        val lantern = TexturedModel.LANTERN.create(block, modelOutput)
        val lanternHanging = TexturedModel.HANGING_LANTERN.create(block, modelOutput)

        val litTex = TextureMapping().put(TextureSlot.LANTERN, block.model("_lit"))
        val litLantern = ModelTemplates.LANTERN.create(block.model("_lit"), litTex, modelOutput)
        val litLanternHanging = ModelTemplates.HANGING_LANTERN.create(block.model("_lit_hanging"), litTex, modelOutput)

        blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.properties(BlockStateProperties.HANGING, BlockStateProperties.LIT)
                    .select(false, false, lantern.toVariant())
                    .select(true, false, lanternHanging.toVariant())
                    .select(false, true, litLantern.toVariant())
                    .select(true, true, litLanternHanging.toVariant())
            )
        )
    }

    private fun BlockModelGenerators.addAxis(block: Block) = blockStateOutput.accept(
        BlockModelGenerators.createAxisAlignedPillarBlock(block, ModelLocationUtils.getModelLocation(block))
    )

    private fun BlockModelGenerators.bookshelf(bookshelf: Block, top: Block) {
        val texture = TextureMapping.column(TextureMapping.getBlockTexture(bookshelf), TextureMapping.getBlockTexture(top))
        val model = ModelTemplates.CUBE_COLUMN.create(bookshelf, texture, modelOutput)
        blockStateOutput.accept(createSimpleBlock(bookshelf, model))
    }

    private fun BlockModelGenerators.denseCube(block: Block) {
        val topModel = ModelLocationUtils.getModelLocation(block, "_top")
        val bottomModel = ModelLocationUtils.getModelLocation(block, "_bottom")
        val itemModel = TexturedModel.CUBE_TOP_BOTTOM.create(block, modelOutput)
        delegateItemModel(block.asItem(), itemModel)
        blockStateOutput.accept(
            MultiPartGenerator.multiPart(block)
                .with(CompositeBlock.UPPER_NORTH_EAST, true, variant(topModel, VariantProperties.Rotation.R90))
                .with(CompositeBlock.UPPER_NORTH_WEST, true, variant(topModel))
                .with(CompositeBlock.UPPER_SOUTH_EAST, true, variant(topModel, VariantProperties.Rotation.R180))
                .with(CompositeBlock.UPPER_SOUTH_WEST, true, variant(topModel, VariantProperties.Rotation.R270))
                .with(CompositeBlock.LOWER_NORTH_EAST, true, variant(bottomModel, VariantProperties.Rotation.R90))
                .with(CompositeBlock.LOWER_NORTH_WEST, true, variant(bottomModel))
                .with(CompositeBlock.LOWER_SOUTH_EAST, true, variant(bottomModel, VariantProperties.Rotation.R180))
                .with(CompositeBlock.LOWER_SOUTH_WEST, true, variant(bottomModel, VariantProperties.Rotation.R270))
        )
    }

    fun BlockModelGenerators.carpetPlate(plate: Block, wool: Block) {
        val up = TexturedModel.CARPET.get(wool).createWithSuffix(plate, "_up", modelOutput)
        val down = TexturedModels.CARPET_DOWN.get(wool).createWithSuffix(plate, "_down", modelOutput)
        delegateItemModel(plate, up)
        blockStateOutput.accept(BlockModelGenerators.createPressurePlate(plate, up, down))
    }

    fun BlockModelGenerators.tintedPane(glass: Block, glassPane: Block) {
        val item = glassPane.asItem()
        ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(item),
            TextureMapping.layer0(glass),
            modelOutput
        )
    }

    fun <T : Comparable<T>> MultiPartGenerator.with(
        property: Property<T>, value: T, vararg variants: Variant,
    ) = with(Condition.condition().term(property, value), *variants)

    fun variant(model: ResourceLocation) = Variant().with(VariantProperties.MODEL, model)
    fun variant(model: ResourceLocation, rotation: VariantProperties.Rotation) =
        variant(model).with(VariantProperties.Y_ROT, rotation).with(VariantProperties.UV_LOCK, true)

}
