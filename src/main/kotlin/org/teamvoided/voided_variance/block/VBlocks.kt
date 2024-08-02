package org.teamvoided.voided_variance.block

import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.block.WallBlock

class VStairsBlock(val block: Block, settings: Settings) : StairsBlock(block.defaultState, settings)
class VSlabBlock(val block: Block, settings: Settings) : SlabBlock(settings)
class VWallBlock(val block: Block, settings: Settings) : WallBlock(settings)
