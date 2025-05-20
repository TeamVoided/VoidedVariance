package org.teamvoided.voided_variance.item

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.Blocks
import net.minecraft.client.item.TooltipConfig
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.PotionContentsComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsage
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.PotionItem
import net.minecraft.particle.ParticleTypes
import net.minecraft.potion.Potions
import net.minecraft.registry.tag.BlockTags
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent
import org.teamvoided.voided_variance.init.VVItems
import org.teamvoided.voided_variance.utils.TINTED_TOOLTIP

open class TintedPotionItem(settings: Settings) : PotionItem(settings) {
    override fun getTranslationKey(stack: ItemStack?): String = super.getTranslationKey()
    override fun appendTooltip(
        stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>, config: TooltipConfig,
    ) {
        val potion = stack.get(DataComponentTypes.POTION_CONTENTS)
        if (potion != null) tooltip.add(
            Text.translatable(TINTED_TOOLTIP).formatted(Formatting.ITALIC, Formatting.DARK_GRAY)
        )
    }

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        val player = user as? PlayerEntity
        if (player is ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(player, stack)
        }

        if (!world.isClient) {
            val potion = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
            potion.forEachEffect { statusEffectInstance: StatusEffectInstance? ->
                if (statusEffectInstance!!.effectType.value().isInstant) {
                    statusEffectInstance.effectType.value().applyInstantEffect(
                        player,
                        player,
                        user,
                        statusEffectInstance.amplifier,
                        1.0
                    )
                } else {
                    user.addStatusEffect(statusEffectInstance)
                }
            }
        }

        if (player != null) {
            player.incrementStat(Stats.USED.getOrCreateStat(this))
            stack.consume(1, player)

            if (!player.isInCreativeMode) {
                if (stack.isEmpty) return getBottle().defaultStack
                if (!player.giveItemStack(getBottle().defaultStack)) {
                    player.dropItem(getBottle().defaultStack, false);
                }
            }
        }

        user.emitGameEvent(GameEvent.DRINK)
        return stack
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val blockPos = context.blockPos
        val player = context.player ?: return ActionResult.PASS
        val stack = context.stack
        val potion = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
        val blockState = world.getBlockState(blockPos)
        if (context.side != Direction.DOWN && blockState.isIn(BlockTags.CONVERTABLE_TO_MUD) && potion.matches(Potions.WATER)) {
            world.playSound(null, blockPos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1.0f, 1.0f)
            player.setStackInHand(context.hand, ItemUsage.exchangeStack(stack, player, getBottle().defaultStack))
            player.incrementStat(Stats.USED.getOrCreateStat(stack.item))
            if (!world.isClient) {
                val serverWorld = world as ServerWorld

                for (i in 0..4) {
                    serverWorld.spawnParticles(
                        ParticleTypes.WATER_SPLASH,
                        blockPos.x.toDouble() + world.random.nextDouble(),
                        (blockPos.y + 1).toDouble(),
                        blockPos.z.toDouble() + world.random.nextDouble(),
                        1, 0.0, 0.0, 0.0, 1.0
                    )
                }
            }

            world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos)
            world.setBlockState(blockPos, Blocks.MUD.defaultState)
            return ActionResult.success(world.isClient)
        }
        return ActionResult.PASS
    }

    open fun getBottle() = VVItems.TINTED_GLASS_BOTTLE
}
