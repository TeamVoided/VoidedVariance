package org.teamvoided.voided_variance.init

import net.minecraft.block.*
import net.minecraft.registry.Registries
import net.minecraft.sound.BlockSoundGroup
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.utils.register

object VVBlocks {
    val BLOCKS = mutableListOf<Block>()

    // Infested blocks
    val INFESTED_MOSSY_COBBLESTONE = register(
        "infested_mossy_cobblestone", InfestedBlock(
            Blocks.MOSSY_COBBLESTONE, AbstractBlock.Settings.create().mapColor(MapColor.CLAY)
        )
    )
    val INFESTED_COBBLED_DEEPSLATE =
        register("infested_cobbled_deepslate", InfestedBlock(Blocks.COBBLED_DEEPSLATE, deepslate()))
    val INFESTED_DEEPSLATE_BRICKS =
        register("infested_deepslate_bricks", InfestedBlock(Blocks.DEEPSLATE_BRICKS, deepslate()))
    val INFESTED_CRACKED_DEEPSLATE_BRICKS =
        register("infested_cracked_deepslate_bricks", InfestedBlock(Blocks.CRACKED_DEEPSLATE_BRICKS, deepslate()))
    val INFESTED_DEEPSLATE_TILES =
        register("infested_deepslate_tiles", InfestedBlock(Blocks.DEEPSLATE_TILES, deepslate()))
    val INFESTED_CRACKED_DEEPSLATE_TILES =
        register("infested_cracked_deepslate_tiles", InfestedBlock(Blocks.CRACKED_DEEPSLATE_TILES, deepslate()))
    val INFESTED_POLISHED_DEEPSLATE =
        register("infested_polished_deepslate", InfestedBlock(Blocks.POLISHED_DEEPSLATE, deepslate()))


    fun init() = Unit


    fun deepslate() = AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE)

    fun register(id: String, item: Block): Block {
        val holder = registerNoItem(id, item)
        VVItems.registerBlockItem(id, holder)
        return holder
    }

    fun registerNoItem(id: String, item: Block): Block {
        val holder = Registries.BLOCK.register(id(id), item)
        BLOCKS.add(holder)
        return holder
    }


}