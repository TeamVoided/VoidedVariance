package org.teamvoided.voided_variance.utils

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

fun isDev() = FabricLoader.getInstance().isDevelopmentEnvironment

fun <T> Registry<T>.registerHolder(id: Identifier, entry: T): Holder.Reference<T> =
    Registry.registerHolder(this, id, entry)

fun <T> Registry<T>.register(id: Identifier, entry: T): T = Registry.register(this, id, entry)

val Item.id get() = Registries.ITEM.getId(this)
val Block.id get() = Registries.BLOCK.getId(this)

fun <T> FabricTagProvider<T>.FabricTagBuilder.addAll(list: Iterable<T>): FabricTagProvider<T>.FabricTagBuilder {
    list.forEach{this.add(it)}
    return this
}

fun Collection<ItemConvertible>.toItems() = this.map(ItemConvertible::asItem)
fun Collection<ItemConvertible>.toStacks() = this.toItems().map(Item::getDefaultStack)
