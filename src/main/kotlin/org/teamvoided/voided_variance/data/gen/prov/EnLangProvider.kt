package org.teamvoided.voided_variance.data.gen.prov

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import net.minecraft.resources.ResourceLocation
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.init.VVTabs
import org.teamvoided.voided_variance.utils.HEAVY_CUBE_TOOLTIP
import org.teamvoided.voided_variance.utils.TINTED_TOOLTIP
import org.teamvoided.voided_variance.utils.id
import java.util.concurrent.CompletableFuture

class EnLangProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(o, r) {

    override fun generateTranslations(lookup: HolderLookup.Provider, gen: TranslationBuilder) {
        VVItems.ITEMS.forEach { gen.add(it, it.id.lang()) }
        VVTabs.VOIDED_VARIANCE.key().let { gen.add(it, it.location().lang()) }

        gen.add(HEAVY_CUBE_TOOLTIP, "This block contains custom state!")
        gen.add(TINTED_TOOLTIP, "This bottle is too dark to make out its contents.")
    }

    private fun ResourceLocation.lang(): String = this.path.titleCase()

    private fun String.titleCase(): String {
        return split("_").joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
    }

}