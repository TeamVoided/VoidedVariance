package org.teamvoided.voided_variance.utils

import net.minecraft.registry.Holder
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


fun <T> Registry<T>.registerHolder(id: Identifier, entry:T): Holder.Reference<T> = Registry.registerHolder(this, id, entry)
fun <T> Registry<T>.register(id: Identifier, entry:T): T = Registry.register(this, id, entry)