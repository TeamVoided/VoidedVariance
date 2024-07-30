package org.teamvoided.voided_variance.block

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.InfestedBlock

class VInfestedBlock(base: Block) : InfestedBlock(base, AbstractBlock.Settings.copy(base))