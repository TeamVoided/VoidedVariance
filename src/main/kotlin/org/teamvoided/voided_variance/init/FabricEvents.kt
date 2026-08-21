package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult

import org.teamvoided.voided_variance.block.CompositeBlock.Companion.POS_TO_CORNER
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.addToComposite
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.getCornerPosition
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.getOffset
import org.teamvoided.voidlib.helpers.map

object FabricEvents {
    fun init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register { builder ->
            builder.addContainer(VVItems.TINTED_POTION)
            builder.addContainer(VVItems.TINTED_SPLASH_POTION)
            builder.addContainer(VVItems.TINTED_LINGERING_POTION)

            builder.addContainerRecipe(VVItems.TINTED_POTION, Items.GUNPOWDER, VVItems.TINTED_SPLASH_POTION)
            builder.addContainerRecipe(VVItems.TINTED_SPLASH_POTION, Items.DRAGON_BREATH, VVItems.TINTED_LINGERING_POTION)
        }

        UseBlockCallback.EVENT.register(::addToCompositeFromCoreItem)
    }

    fun addToCompositeFromCoreItem(player: Player, level: Level, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        if (hit.type != HitResult.Type.BLOCK) return InteractionResult.PASS
        val stack = player.getItemInHand(hand)
        if (!stack.`is`(Items.HEAVY_CORE)) return InteractionResult.PASS

        val hitState = level.getBlockState(hit.blockPos)
        if (hitState.`is`(VVBlocks.HEAVY_CUBE)) {
            val corner = POS_TO_CORNER[getCornerPosition(hit).add(hit.direction.getOffset().map { it * -2 })]
            if (corner != null && !hitState.getValue(corner)) return InteractionResult.PASS
        }

        val pos = hit.blockPos.relative(hit.direction)
        val state = level.getBlockState(pos)
        if (!state.`is`(VVBlocks.HEAVY_CUBE)) return InteractionResult.PASS
        if (!level.mayInteract(player, pos)) return InteractionResult.PASS

        val clickedPos = getCornerPosition(BlockHitResult(hit.location, hit.direction.opposite, pos, hit.isInside))
        val cornerToBeAdded = POS_TO_CORNER[clickedPos] ?: return InteractionResult.PASS
        addToComposite(state, cornerToBeAdded, level, pos, player, stack)
        return InteractionResult.SUCCESS
    }
}