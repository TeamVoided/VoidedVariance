package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.util.Identifier
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.init.VVTabs
import org.teamvoided.voided_variance.utils.id
import java.util.concurrent.CompletableFuture

class EnLangProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(o, r) {

    override fun generateTranslations(lookup: HolderLookup.Provider, gen: TranslationBuilder) {
        VVItems.ITEMS.forEach{ gen.add(it, it.id.lang())}
        VVTabs.DUSK_AUTUMN_TAB.registryKey.let { gen.add(it, it.value.lang()) }
    }

    private fun Identifier.lang(): String = this.path.titleCase()

    private fun String.titleCase(): String {
        return split("_").joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
    }

}