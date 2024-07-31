package org.teamvoided.voided_variance.utils

import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.Texture

fun BlockStateModelGenerator.fence(fenceBlock: Block, reference: Block) {
    val texture = Texture.texture(reference)
    val post = Models.FENCE_POST.upload(fenceBlock, texture, this.modelCollector)
    val side = Models.FENCE_SIDE.upload(fenceBlock, texture, this.modelCollector)
    val inventory = Models.FENCE_INVENTORY.upload(fenceBlock, texture, this.modelCollector)
    this.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(fenceBlock, post, side))
    this.registerParentedItemModel(fenceBlock, inventory)
}