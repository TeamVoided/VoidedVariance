package org.teamvoided.voided_variance.item


import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.Direction
import net.minecraft.core.component.DataComponents
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.tags.BlockTags
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.PotionItem
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.gameevent.GameEvent
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.appendTintedTooltip
import org.teamvoided.voided_variance.utils.giveItem

open class TintedPotionItem(properties: Properties) : PotionItem(properties) {

    override fun getDescriptionId(stack: ItemStack): String = super.getDescriptionId()

    override fun appendHoverText(
        stack: ItemStack, ctx: TooltipContext, tooltips: MutableList<Component>, flag: TooltipFlag,
    ) {
        appendTintedTooltip(stack, tooltips)
    }

    override fun finishUsingItem(stack: ItemStack, level: Level, user: LivingEntity): ItemStack {
        val player = user as? Player
        if (player is ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
        }

        if (!level.isClientSide) {
            val potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
            potion.forEachEffect { effect ->
                if (effect.effect.value().isInstantenous)
                    effect.effect.value().applyInstantenousEffect(player, player, user, effect.amplifier, 1.0)
                else
                    user.addEffect(effect)
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(stack.item))
            stack.consume(1, player)

            if (!player.hasInfiniteMaterials()) {
                if (stack.isEmpty) {
                    return getBottle()
                }
                player.giveItem(getBottle())
            }
        }

        user.gameEvent(GameEvent.DRINK)
        return stack
    }

    override fun useOn(ctx: UseOnContext): InteractionResult {
        val level = ctx.level
        val pos = ctx.clickedPos
        val player = ctx.player ?: return InteractionResult.PASS
        val stack = ctx.itemInHand
        val potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
        val state = level.getBlockState(pos)
        if (ctx.clickedFace != Direction.DOWN && state.`is`(BlockTags.CONVERTABLE_TO_MUD) && potion.`is`(Potions.WATER)) {
            level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0f, 1.0f)
            player.setItemInHand(ctx.hand, ItemUtils.createFilledResult(stack, player, getBottle()))
            player.awardStat(Stats.ITEM_USED.get(stack.item))
            if (level is ServerLevel) {
                repeat(4) {
                    level.sendParticles(
                        ParticleTypes.SPLASH,
                        pos.x.toDouble() + level.random.nextDouble(),
                        (pos.y + 1).toDouble(),
                        pos.z.toDouble() + level.random.nextDouble(),
                        1, 0.0, 0.0, 0.0, 1.0
                    )
                }
            }

            level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f)
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos)
            level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState())
            return InteractionResult.sidedSuccess(level.isClientSide)
        }
        return super.useOn(ctx)
    }

    open fun getBottle(): ItemStack = VVItems.TINTED_GLASS_BOTTLE.defaultInstance
}
