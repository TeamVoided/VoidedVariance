package org.teamvoided.voided_variance.init

import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings.copy
import net.minecraft.registry.Registries
import net.minecraft.sound.BlockSoundGroup
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.utils.register

@Suppress("unused")
object VVBlocks {
    val BLOCKS = mutableListOf<Block>()

    val CUTOUT_BLOCKS = mutableSetOf<Block>()

    val SWORDABLE = mutableSetOf<Block>()
    val PICKAXABLE = mutableSetOf<Block>()
    val AXABLE = mutableSetOf<Block>()
    val SHOVELABLE = mutableSetOf<Block>()
    val HOEABLE = mutableSetOf<Block>()

    // Infested blocks
    val INFESTED_MOSSY_COBBLESTONE = register(
        "infested_mossy_cobblestone", InfestedBlock(
            Blocks.MOSSY_COBBLESTONE, AbstractBlock.Settings.create().mapColor(MapColor.CLAY)
        )
    ).pickaxe()
    val INFESTED_COBBLED_DEEPSLATE =
        register("infested_cobbled_deepslate", InfestedBlock(Blocks.COBBLED_DEEPSLATE, deepslate())).pickaxe()
    val INFESTED_DEEPSLATE_BRICKS =
        register("infested_deepslate_bricks", InfestedBlock(Blocks.DEEPSLATE_BRICKS, deepslate())).pickaxe()
    val INFESTED_CRACKED_DEEPSLATE_BRICKS =
        register("infested_cracked_deepslate_bricks", InfestedBlock(Blocks.CRACKED_DEEPSLATE_BRICKS, deepslate())).pickaxe()
    val INFESTED_DEEPSLATE_TILES =
        register("infested_deepslate_tiles", InfestedBlock(Blocks.DEEPSLATE_TILES, deepslate())).pickaxe()
    val INFESTED_CRACKED_DEEPSLATE_TILES =
        register("infested_cracked_deepslate_tiles", InfestedBlock(Blocks.CRACKED_DEEPSLATE_TILES, deepslate())).pickaxe()
    val INFESTED_POLISHED_DEEPSLATE =
        register("infested_polished_deepslate", InfestedBlock(Blocks.POLISHED_DEEPSLATE, deepslate())).pickaxe()

    // Brick fence
    val BRICK_FENCE = register("brick_fence", FenceBlock(copy(Blocks.BRICKS))).pickaxe()

    fun init() = Unit



    fun Block.cutout(): Block {
        CUTOUT_BLOCKS.add(this)
        return this
    }

    fun Block.sword(): Block {
        SWORDABLE.add(this)
        return this
    }

    private fun Block.pickaxe(): Block {
        PICKAXABLE.add(this)
        return this
    }

    fun Block.axe(): Block {
        AXABLE.add(this)
        return this
    }

    fun Block.shovel(): Block {
        SHOVELABLE.add(this)
        return this
    }

    fun Block.hoe(): Block {
        HOEABLE.add(this)
        return this
    }


    private fun deepslate(): AbstractBlock.Settings =
        AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE)

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