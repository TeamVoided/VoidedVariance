package org.teamvoided.voided_variance.data.tags

import net.minecraft.core.registries.Registries
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.utils.tag

object CBlockTags {

    val SANDSTONE_WALLS = tag("sandstone_walls")
    val UNCOLORED_SANDSTONE_WALLS = tag("uncolored_sandstone_walls")
    val RED_SANDSTONE_WALLS = tag("red_sandstone_walls")

    fun tag(path: String) = Registries.BLOCK.tag(id("c", path))

}