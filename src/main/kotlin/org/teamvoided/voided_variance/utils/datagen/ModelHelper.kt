package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import java.util.*


fun Block.model(): ResourceLocation = ModelLocationUtils.getModelLocation(this)
fun Block.model(sfx: String): ResourceLocation = model().withSuffix(sfx)

fun ResourceLocation.toVariant(): Variant = Variant.variant().with(VariantProperties.MODEL, this)

fun BlockModelGenerators.fence(fenceBlock: Block, reference: Block) {
    val texture = TextureMapping.defaultTexture(reference)
    val post = ModelTemplates.FENCE_POST.create(fenceBlock, texture, modelOutput)
    val side = ModelTemplates.FENCE_SIDE.create(fenceBlock, texture, modelOutput)
    val inventory = ModelTemplates.FENCE_INVENTORY.create(fenceBlock, texture, modelOutput)
    blockStateOutput.accept(BlockModelGenerators.createFence(fenceBlock, post, side))
    delegateItemModel(fenceBlock, inventory)
}


//shamelessley stolen from ~~voidUtils~~ dusk :)
// slab
fun BlockModelGenerators.slab(block: Block) = slab(block, block)
fun BlockModelGenerators.slab(block: Block, texture: Block) =
    slab(block, texture, texture, texture, texture)

fun BlockModelGenerators.slab(block: Block, texture: Block, full: Block) =
    slab(block, slabTexture(texture), full)

fun BlockModelGenerators.slab(block: Block, bottom: Block, side: Block, top: Block, full: Block) =
    slab(
        block, TextureMapping.defaultTexture(block.model())
            .put(TextureSlot.BOTTOM, bottom.model())
            .put(TextureSlot.SIDE, side.model())
            .put(TextureSlot.TOP, top.model()),
        full
    )

fun BlockModelGenerators.slab(block: Block, texture: TextureMapping, full: Block) {
    val id = ModelTemplates.SLAB_BOTTOM.create(block, texture, modelOutput)
    val id2 = ModelTemplates.SLAB_TOP.create(block, texture, modelOutput)
    val id3 = full.model()
    blockStateOutput.accept(BlockModelGenerators.createSlab(block, id, id2, id3))
    delegateItemModel(block, id)
}

fun slabTexture(texture: Block): TextureMapping = TextureMapping.defaultTexture(texture.model())
    .put(TextureSlot.BOTTOM, texture.model())
    .put(TextureSlot.SIDE, texture.model())
    .put(TextureSlot.TOP, texture.model())

// stairs
fun BlockModelGenerators.stairs(block: Block) =
    stairs(block, block, block, block, block)

fun BlockModelGenerators.stairs(block: Block, texture: Block) =
    stairs(block, texture, texture, texture, texture)

fun BlockModelGenerators.stairs(block: Block, parent: Block, bottom: Block, side: Block, top: Block) =
    stairs(block, parent, bottom.model(), side.model(), top.model())

fun BlockModelGenerators.stairs(block: Block, ends: ResourceLocation, side: ResourceLocation) =
    stairs(block, block, ends, side, ends)

fun BlockModelGenerators.stairs(
    block: Block,
    parent: Block,
    bottom: ResourceLocation,
    side: ResourceLocation,
    top: ResourceLocation,
) {
    val texture  = TextureMapping.defaultTexture(parent)
        .put(TextureSlot.BOTTOM, bottom)
        .put(TextureSlot.SIDE, side)
        .put(TextureSlot.TOP, top)
    val id: ResourceLocation = ModelTemplates.STAIRS_INNER.create(block, texture, modelOutput)
    val id2: ResourceLocation = ModelTemplates.STAIRS_STRAIGHT.create(block, texture, modelOutput)
    val id3: ResourceLocation = ModelTemplates.STAIRS_OUTER.create(block, texture, modelOutput)

    blockStateOutput.accept(BlockModelGenerators.createStairs(block, id, id2, id3))
    delegateItemModel(block, id2)
}

// wall
fun BlockModelGenerators.wall(block: Block) = wall(block, block.model())
fun BlockModelGenerators.wall(block: Block, texture: Block) = wall(block, texture.model())

fun BlockModelGenerators.wall(wallBlock: Block, inId: ResourceLocation) {
    val texture = TextureMapping.defaultTexture(wallBlock.model()).put(TextureSlot.WALL, inId)
    val id = ModelTemplates.WALL_POST.create(wallBlock, texture, modelOutput)
    val id2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, texture, modelOutput)
    val id3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, texture, modelOutput)
    blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, id, id2, id3))
    delegateItemModel(wallBlock, ModelTemplates.WALL_INVENTORY.create(wallBlock, texture, modelOutput))
}

// offset wall
fun BlockModelGenerators.wallOffset(block: Block) = wallOffset(block, block.model())
fun BlockModelGenerators.wallOffset(block: Block, texture: Block) = wallOffset(block, texture.model())
fun BlockModelGenerators.wallOffset(wallBlock: Block, inId: ResourceLocation) {
    val texture = TextureMapping.defaultTexture(wallBlock.model()).put(TextureSlot.WALL, inId)
    val id = OFFSET_WALL_POST.create(wallBlock, texture, modelOutput)
    val id2 = OFFSET_WALL_SIDE.create(wallBlock, texture, modelOutput)
    val id3 = OFFSET_WALL_SIDE_TALL.create(wallBlock, texture, modelOutput)
    blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, id, id2, id3))
    delegateItemModel(wallBlock, OFFSET_WALL_INVENTORY.create(wallBlock, texture, modelOutput))
}

fun <T : Any> T.myb() = Optional.of(this)