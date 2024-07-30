package org.teamvoided.voided_variance.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.voided_variance.VoidedVariance.log

@Suppress("unused")
class VoidedVarianceData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

//        pack.addProvider(::TemplateWorldGenerator)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
//        gen.add(RegistryKeys.BIOME, TemplateBiomes::boostrap)
    }
}
