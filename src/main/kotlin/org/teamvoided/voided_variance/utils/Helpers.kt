package org.teamvoided.voided_variance.utils

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import org.teamvoided.voided_variance.VoidedVariance.MODID

fun isDev() = FabricLoader.getInstance().isDevelopmentEnvironment

fun <T : Any> isModHolder(holder: Holder<T>) = holder.`is` { it.location().namespace == MODID }

fun <T : Any> getModHolders(registry: Registry<T>): List<Holder.Reference<T>> = registry.holders()
    .filter(::isModHolder)
    .toList()

fun <T : Any> getModEntries(registry: Registry<T>): List<T> = registry.holders()
    .filter(::isModHolder)
    .map(Holder<T>::value)
    .toList()

fun <V : Any, T : V> Registry<V>.register(id: ResourceLocation, entry: T): T = Registry.register(this, id, entry)
fun <V : Any, T : V> Registry<T>.registerHolder(id: ResourceLocation, entry: T): Holder.Reference<T> =
    Registry.registerForHolder(this, id, entry)

fun <T : Any, R : Registry<T>> ResourceKey<R>.tag(id: ResourceLocation): TagKey<T> = TagKey.create(this, id)
fun <T : Any, R : Registry<T>> ResourceKey<R>.key(id: ResourceLocation): ResourceKey<T> = ResourceKey.create(this, id)

val Item.id get() = BuiltInRegistries.ITEM.getKey(this)
val Block.id get() = BuiltInRegistries.BLOCK.getKey(this)

fun <T> FabricTagProvider<T>.FabricTagBuilder.addAll(list: Iterable<T>): FabricTagProvider<T>.FabricTagBuilder {
    list.forEach { this.add(it) }
    return this
}

fun Collection<ItemLike>.toItems() = this.map(ItemLike::asItem)
fun Collection<ItemLike>.toStacks() = this.toItems().map(Item::getDefaultInstance)


fun Player.giveItem(stack: ItemStack) {
    if (!addItem(stack)) {
        drop(stack, false)
    }
}
