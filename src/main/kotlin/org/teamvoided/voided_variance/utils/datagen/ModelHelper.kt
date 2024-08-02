package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier
import org.teamvoided.voided_variance.VoidedVariance.id
import java.util.*


fun Block.model(): Identifier = ModelIds.getBlockModelId(this)
fun Block.model(str: String) = this.model().suffix(str)
fun Identifier.suffix(str: String) = Identifier.of(this.namespace, "${this.path}$str")

fun Identifier.toVariant(): BlockStateVariant = BlockStateVariant.create().put(VariantSettings.MODEL, this)

fun BlockStateModelGenerator.fence(fenceBlock: Block, reference: Block) {
    val texture = Texture.texture(reference)
    val post = Models.FENCE_POST.upload(fenceBlock, texture, this.modelCollector)
    val side = Models.FENCE_SIDE.upload(fenceBlock, texture, this.modelCollector)
    val inventory = Models.FENCE_INVENTORY.upload(fenceBlock, texture, this.modelCollector)
    this.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(fenceBlock, post, side))
    this.registerParentedItemModel(fenceBlock, inventory)
}


//shamelessley stolen from ~~voidUtils~~ dusk :)
// slab
fun BlockStateModelGenerator.slab(block: Block) = slab(block, block)
fun BlockStateModelGenerator.slab(block: Block, texture: Block) =
    slab(block, texture, texture, texture, texture)

fun BlockStateModelGenerator.slab(block: Block, texture: Block, full: Block) =
    slab(block, slabTexture(texture), full)

fun BlockStateModelGenerator.slab(block: Block, bottom: Block, side: Block, top: Block, full: Block) =
    slab(
        block, Texture.texture(block.model())
            .put(TextureKey.BOTTOM, bottom.model())
            .put(TextureKey.SIDE, side.model())
            .put(TextureKey.TOP, top.model()),
        full
    )

fun BlockStateModelGenerator.slab(block: Block, texture: Texture, full: Block) {
    val id = Models.SLAB.upload(block, texture, this.modelCollector)
    val id2 = Models.SLAB_TOP.upload(block, texture, this.modelCollector)
    val id3 = full.model()
    this.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, id, id2, id3))
    this.registerParentedItemModel(block, id)
}

fun slabTexture(texture: Block): Texture = Texture.texture(texture.model())
    .put(TextureKey.BOTTOM, texture.model())
    .put(TextureKey.SIDE, texture.model())
    .put(TextureKey.TOP, texture.model())

// stairs
fun BlockStateModelGenerator.stairs(block: Block) =
    stairs(block, block, block, block, block)

fun BlockStateModelGenerator.stairs(block: Block, texture: Block) =
    stairs(block, texture, texture, texture, texture)

fun BlockStateModelGenerator.stairs(block: Block, parent: Block, bottom: Block, side: Block, top: Block) =
    stairs(block, parent, bottom.model(), side.model(), top.model())

fun BlockStateModelGenerator.stairs(block: Block, ends: Identifier, side: Identifier) =
    stairs(block, block, ends, side, ends)

fun BlockStateModelGenerator.stairs(
    block: Block,
    parent: Block,
    bottom: Identifier,
    side: Identifier,
    top: Identifier,
) {
    val texture: Texture = Texture.texture(parent)
        .put(TextureKey.BOTTOM, bottom)
        .put(TextureKey.SIDE, side)
        .put(TextureKey.TOP, top)
    val id: Identifier = Models.INNER_STAIRS.upload(block, texture, this.modelCollector)
    val id2: Identifier = Models.STAIRS.upload(block, texture, this.modelCollector)
    val id3: Identifier = Models.OUTER_STAIRS.upload(block, texture, this.modelCollector)

    this.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, id, id2, id3))
    this.registerParentedItemModel(block, id2)
}

// wall
fun BlockStateModelGenerator.wall(block: Block) = wall(block, block.model())
fun BlockStateModelGenerator.wall(block: Block, texture: Block) = wall(block, texture.model())

fun BlockStateModelGenerator.wall(wallBlock: Block, inId: Identifier) {
    val texture = Texture.texture(wallBlock.model()).put(TextureKey.WALL, inId)
    val id = Models.TEMPLATE_WALL_POST.upload(wallBlock, texture, this.modelCollector)
    val id2 = Models.TEMPLATE_WALL_SIDE.upload(wallBlock, texture, this.modelCollector)
    val id3 = Models.TEMPLATE_WALL_SIDE_TALL.upload(wallBlock, texture, this.modelCollector)
    this.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(wallBlock, id, id2, id3))
    this.registerParentedItemModel(wallBlock, Models.WALL_INVENTORY.upload(wallBlock, texture, this.modelCollector))
}

// offset wall
fun BlockStateModelGenerator.wallOffset(block: Block) = wallOffset(block, block.model())
fun BlockStateModelGenerator.wallOffset(block: Block, texture: Block) = wallOffset(block, texture.model())
fun BlockStateModelGenerator.wallOffset(wallBlock: Block, inId: Identifier) {
    val texture = Texture.texture(wallBlock.model()).put(TextureKey.WALL, inId)
    val id = OFFSET_WALL_POST.upload(wallBlock, texture, this.modelCollector)
    val id2 = Models.TEMPLATE_WALL_SIDE.upload(wallBlock, texture, this.modelCollector)
    val id3 = Models.TEMPLATE_WALL_SIDE_TALL.upload(wallBlock, texture, this.modelCollector)
    this.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(wallBlock, id, id2, id3))
    this.registerParentedItemModel(wallBlock, OFFSET_WALL_INVENTORY.upload(wallBlock, texture, this.modelCollector))
}

val OFFSET_WALL_POST = block("template/offset_wall_post", "_post", TextureKey.WALL)
val OFFSET_WALL_INVENTORY = block("template/offset_wall_inventory", "_inventory", TextureKey.WALL)

fun block(parent: String, variant: String, vararg requiredTextures: TextureKey): Model =
    Model(id("block/$parent").myb(), variant.myb(), *requiredTextures)

fun <T : Any> T.myb() = Optional.of(this)