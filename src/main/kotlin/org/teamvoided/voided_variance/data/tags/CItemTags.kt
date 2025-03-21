package org.teamvoided.voided_variance.data.tags

import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

object CItemTags {
    val SANDSTONE_WALLS = c("sandstone_walls")
    val UNCOLORED_SANDSTONE_WALLS = c("uncolored_sandstone_walls")
    val RED_SANDSTONE_WALLS = c("red_sandstone_walls")
    private fun c(path: String) = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", path))
}