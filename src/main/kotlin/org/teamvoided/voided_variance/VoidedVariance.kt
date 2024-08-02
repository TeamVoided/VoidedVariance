package org.teamvoided.voided_variance

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.init.VVTabs

@Suppress("unused")
object VoidedVariance {
    const val MODID = "voided_variance"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(VoidedVariance::class.simpleName)

    fun init() {
        VVItems.init()
        VVBlocks.init()
        VVTabs.init()
    }

    fun id(path: String) = Identifier.of(MODID, path)
    fun mc(path: String) = Identifier.ofDefault(path)
}
