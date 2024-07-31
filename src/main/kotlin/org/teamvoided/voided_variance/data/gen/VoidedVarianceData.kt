package org.teamvoided.voided_variance.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.registry.RegistrySetBuilder
import org.teamvoided.voided_variance.VoidedVariance.log
import org.teamvoided.voided_variance.data.gen.prov.BlockLootTableProvider
import org.teamvoided.voided_variance.data.gen.prov.EnLangProvider
import org.teamvoided.voided_variance.data.gen.prov.ModelProvider
import org.teamvoided.voided_variance.data.gen.prov.RecipeProvider
import org.teamvoided.voided_variance.data.gen.tags.BlockTagProvider
import org.teamvoided.voided_variance.data.gen.tags.ItemTagProvider

@Suppress("unused")
class VoidedVarianceData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        log.info("Hello from DataGen")
        val pack = gen.createPack()

        pack.addProvider(::ModelProvider)
        pack.addProvider(::EnLangProvider)
        pack.addProvider(::BlockLootTableProvider)
        pack.addProvider(::RecipeProvider)

        val blockTags = pack.addProvider(::BlockTagProvider)
        pack.addProvider { o, r -> ItemTagProvider(o, r, blockTags) }
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
//        gen.add(RegistryKeys.BIOME, TemplateBiomes::boostrap)
    }
}
