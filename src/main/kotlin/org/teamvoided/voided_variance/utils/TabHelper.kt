package org.teamvoided.voided_variance.utils

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemGroup
import net.minecraft.registry.RegistryKey

fun ItemGroup.ItemStackCollector.addItem(vararg list: ItemConvertible) = this.addItems(list.toList())
fun ItemGroup.ItemStackCollector.addItems(list: Collection<ItemConvertible>) = this.addStacks(list.toStacks())
fun ItemGroup.ItemStackCollector.addLists(vararg lists: Collection<ItemConvertible>) =
    this.addStacks(lists.flatMap { it.toStacks() })

fun addToTab(itemGroup: RegistryKey<ItemGroup>, itemBefore: ItemGroupEvents.ModifyEntries) =
    ItemGroupEvents.modifyEntriesEvent(itemGroup).register(itemBefore)

fun FabricItemGroupEntries.addAfter(item: ItemConvertible, list: Collection<ItemConvertible>) =
    this.addAfter(item.asItem(), list.map { it.asItem().defaultStack })