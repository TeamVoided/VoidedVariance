package org.teamvoided.voided_variance

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object VoidedVariance {
    const val MODID = "voided_variance"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(VoidedVariance::class.simpleName)

    fun init() {
        log.info("Hello from Common")
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
