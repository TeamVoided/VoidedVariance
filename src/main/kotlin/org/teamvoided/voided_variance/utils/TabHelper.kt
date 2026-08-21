package org.teamvoided.voided_variance.utils

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.CreativeModeTab.Output
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.ItemLike


fun Output.addItem(vararg list: ItemLike) = addItems(list.toList())
fun Output.addItems(list: Collection<ItemLike>) = acceptAll(list.toStacks())
fun Output.addLists(vararg lists: Collection<ItemLike>) = acceptAll(lists.flatMap { it.toStacks() })

fun addToTab(itemGroup: ResourceKey<CreativeModeTab>, itemBefore: ItemGroupEvents.ModifyEntries) {
    ItemGroupEvents.modifyEntriesEvent(itemGroup).register(itemBefore)
}

fun FabricItemGroupEntries.addAfter(item: ItemLike, list: Collection<ItemLike>) {
    addAfter(item.asItem(), list.map { it.asItem().defaultInstance })
}

fun Output.generatePotionEntries(params: ItemDisplayParameters, potionItem: Item) {
    params.holders().lookup(Registries.POTION).ifPresent { potion ->
        potion.listElements()
            .map { reference -> PotionContents.createItemStack(potionItem, reference) }
            .forEach(this::accept)
    }
}
