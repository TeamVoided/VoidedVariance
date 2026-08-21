package org.teamvoided.voided_variance

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.voided_variance.init.FabricEvents
import org.teamvoided.voided_variance.init.VVBlockSetTypes
import org.teamvoided.voided_variance.init.VVBlocks
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.init.VVRecipeTypes
import org.teamvoided.voided_variance.init.VVTabs

@Suppress("unused")
object VoidedVariance {
    const val MODID = "voided_variance"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(VoidedVariance::class.simpleName)

    fun init() {
        log.info("My Void, your Variance")
        VVBlockSetTypes.init()
        VVItems.init()
        VVBlocks.init()
        VVTabs.init()
        VVRecipeTypes.init()
        FabricEvents.init()
    }

    fun id(ns: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(ns, path)
    fun id(path: String) = id(MODID, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)

}