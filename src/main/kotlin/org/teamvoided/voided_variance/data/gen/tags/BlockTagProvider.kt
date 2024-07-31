package org.teamvoided.voided_variance.data.gen.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.HolderLookup
import java.util.concurrent.CompletableFuture

class BlockTagProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(o, r) {
    override fun configure(wrapperLookup: HolderLookup.Provider) {
    }
}
