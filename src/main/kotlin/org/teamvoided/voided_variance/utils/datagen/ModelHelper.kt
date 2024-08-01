package org.teamvoided.voided_variance.utils.datagen

import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier


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