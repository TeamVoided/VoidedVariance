package org.teamvoided.voided_variance.block

import net.fabricmc.fabric.api.block.BlockPickInteractionAware
import net.minecraft.block.*
import net.minecraft.client.item.TooltipConfig
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.BlockStateComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties.WATERLOGGED
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.ItemInteractionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import org.teamvoided.voided_variance.utils.HEAVY_CUBE_TOOLTIP
import org.teamvoided.voidlib.helpers.map
import org.teamvoided.voidlib.helpers.playBlockSound
import org.teamvoided.voidlib.helpers.scheduleFluidTick
import net.minecraft.util.ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION as PASS_TO_DEFAULT

class CompositeBlock(settings: Settings) : HeavyCoreBlock(settings), BlockPickInteractionAware {

    init {
        defaultState = defaultState
            .with(UPPER_NORTH_EAST, true).with(UPPER_NORTH_WEST, true)
            .with(UPPER_SOUTH_EAST, true).with(UPPER_SOUTH_WEST, true)
            .with(LOWER_NORTH_EAST, true).with(LOWER_NORTH_WEST, true)
            .with(LOWER_SOUTH_EAST, true).with(LOWER_SOUTH_WEST, true)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, entity: PlayerEntity, hitResult: BlockHitResult)
            : ActionResult {
        val mainStack = entity.getStackInHand(Hand.MAIN_HAND)
        val offStack = entity.getStackInHand(Hand.OFF_HAND)
        if (entity.isSneaking && mainStack.isEmpty && offStack.isEmpty && hitResult.type == HitResult.Type.BLOCK) {
            val cornerProperty = POS_TO_CORNER[getCornerPosition(hitResult)]
            if (cornerProperty != null && state.get(cornerProperty)) {
                val newState = state.with(cornerProperty, false)
                world.setBlockState(pos, newState)
                if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, state)
                if (!(entity.isCreative && entity.inventory.contains(Items.HEAVY_CORE.defaultStack))) {
                    entity.giveItemStack(ItemStack(Items.HEAVY_CORE))
                }
                if (!newState.hasAnyCorners()) {
                    val replaceState =
                        if (newState.get(WATERLOGGED)) newState.fluidState.blockState else Blocks.AIR.defaultState
                    world.setBlockState(pos, replaceState)
                }
                world.playBlockSound(pos, SoundEvents.BLOCK_HEAVY_CORE_BREAK, 0.8f, 1.0f)
                return ActionResult.SUCCESS
            }
        }
        return super.onUse(state, world, pos, entity, hitResult)
    }

    override fun onInteract(
        stack: ItemStack, state: BlockState, world: World,
        pos: BlockPos, entity: PlayerEntity, hand: Hand, hitResult: BlockHitResult
    ): ItemInteractionResult {
        if (hitResult.type != HitResult.Type.BLOCK || !stack.isOf(Items.HEAVY_CORE) || state.isFull())
            return super.onInteract(stack, state, world, pos, entity, hand, hitResult)

        val clickedPos = getCornerPosition(hitResult).add(hitResult.side.getOffset().map { it * -2 })
        val cornerToBeAdded = POS_TO_CORNER[clickedPos] ?: return PASS_TO_DEFAULT

        world.setBlockState(pos, state.with(cornerToBeAdded, true))
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, state)
        if (!entity.isCreative) stack.decrement(1)
        world.playBlockSound(pos, SoundEvents.BLOCK_HEAVY_CORE_PLACE, 0.8f, 1.0f)
        return ItemInteractionResult.SUCCESS
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        var state = super.getPlacementState(ctx) ?: return null
        ctx.stack.get(DataComponentTypes.BLOCK_STATE)?.let { state = it.apply(state) }
        return state
    }

    override fun getPickedStack(
        state: BlockState, world: BlockView, pos: BlockPos, player: PlayerEntity, result: HitResult
    ): ItemStack {
        val stack = state.block.asItem().defaultStack
        if (state.block !is CompositeBlock || stack.isEmpty || !player.isCreative || !player.isSneaking || state.isFull()) return stack
        var data = stack.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent(mapOf()))
        for (property in PROPS) data = data.with(property, state)
        stack.set(DataComponentTypes.BLOCK_STATE, data)
        stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
        return stack
    }

    override fun appendTooltip(
        stack: ItemStack, tooltipContext: Item.TooltipContext, tooltip: MutableList<Text>, options: TooltipConfig
    ) {
        super.appendTooltip(stack, tooltipContext, tooltip, options)
        stack.get(DataComponentTypes.BLOCK_STATE)?.let {
            tooltip.add(Text.translatable(HEAVY_CUBE_TOOLTIP).formatted(Formatting.RED))
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(
            UPPER_NORTH_WEST, UPPER_NORTH_EAST,
            UPPER_SOUTH_WEST, UPPER_SOUTH_EAST,
            LOWER_NORTH_WEST, LOWER_NORTH_EAST,
            LOWER_SOUTH_WEST, LOWER_SOUTH_EAST
        )
    }

    override fun getOutlineShape(
        state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext
    ): VoxelShape {
        val list = mutableListOf<VoxelShape>()

        if (state.get(UPPER_NORTH_EAST)) list.add(UPPER_TOP_RIGHT_SHAPE)
        if (state.get(UPPER_NORTH_WEST)) list.add(UPPER_TOP_LEFT_SHAPE)
        if (state.get(UPPER_SOUTH_EAST)) list.add(UPPER_BOTTOM_RIGHT_SHAPE)
        if (state.get(UPPER_SOUTH_WEST)) list.add(UPPER_BOTTOM_LEFT_SHAPE)

        if (state.get(LOWER_NORTH_EAST)) list.add(LOWER_TOP_RIGHT_SHAPE)
        if (state.get(LOWER_NORTH_WEST)) list.add(LOWER_TOP_LEFT_SHAPE)
        if (state.get(LOWER_SOUTH_EAST)) list.add(LOWER_BOTTOM_RIGHT_SHAPE)
        if (state.get(LOWER_SOUTH_WEST)) list.add(LOWER_BOTTOM_LEFT_SHAPE)

        if (list.isEmpty()) return VoxelShapes.fullCube()

        return VoxelShapes.union(VoxelShapes.empty(), *list.toTypedArray())
    }

    companion object {
        fun getCornerPosition(hitResult: BlockHitResult): Vec3d =
            hitResult.pos.add(hitResult.side.getOffset())
                .map { it % 1 }
                .map { if (it < 0) 1 + it else it }
                .map { if (it < .5) .25 else .75 }

        fun BlockState.hasAnyCorners(): Boolean =
            this.get(UPPER_NORTH_EAST) || this.get(UPPER_NORTH_WEST)
                    || this.get(UPPER_SOUTH_EAST) || this.get(UPPER_SOUTH_WEST)
                    || this.get(LOWER_NORTH_EAST) || this.get(LOWER_NORTH_WEST)
                    || this.get(LOWER_SOUTH_EAST) || this.get(LOWER_SOUTH_WEST)

        fun BlockState.isFull() = block is CompositeBlock &&
                this.get(UPPER_NORTH_EAST) && this.get(UPPER_NORTH_WEST)
                && this.get(UPPER_SOUTH_EAST) && this.get(UPPER_SOUTH_WEST)
                && this.get(LOWER_NORTH_EAST) && this.get(LOWER_NORTH_WEST)
                && this.get(LOWER_SOUTH_EAST) && this.get(LOWER_SOUTH_WEST)


        val UPPER_NORTH_EAST: BooleanProperty = BooleanProperty.of("upper_north_east")
        val UPPER_NORTH_WEST: BooleanProperty = BooleanProperty.of("upper_north_west")
        val UPPER_SOUTH_EAST: BooleanProperty = BooleanProperty.of("upper_south_east")
        val UPPER_SOUTH_WEST: BooleanProperty = BooleanProperty.of("upper_south_west")

        val LOWER_NORTH_EAST: BooleanProperty = BooleanProperty.of("lower_north_east")
        val LOWER_NORTH_WEST: BooleanProperty = BooleanProperty.of("lower_north_west")
        val LOWER_SOUTH_EAST: BooleanProperty = BooleanProperty.of("lower_south_east")
        val LOWER_SOUTH_WEST: BooleanProperty = BooleanProperty.of("lower_south_west")

        val PROPS = setOf(
            UPPER_NORTH_EAST, UPPER_NORTH_WEST,
            UPPER_SOUTH_EAST, UPPER_SOUTH_WEST,
            LOWER_NORTH_EAST, LOWER_NORTH_WEST,
            LOWER_SOUTH_EAST, LOWER_SOUTH_WEST
        )

        val UPPER_TOP_RIGHT_SHAPE: VoxelShape = createCuboidShape(8.0, 8.0, 0.0, 16.0, 16.0, 8.0)
        val UPPER_TOP_LEFT_SHAPE: VoxelShape = createCuboidShape(0.0, 8.0, 0.0, 8.0, 16.0, 8.0)
        val UPPER_BOTTOM_RIGHT_SHAPE: VoxelShape = createCuboidShape(8.0, 8.0, 8.0, 16.0, 16.0, 16.0)
        val UPPER_BOTTOM_LEFT_SHAPE: VoxelShape = createCuboidShape(0.0, 8.0, 8.0, 8.0, 16.0, 16.0)

        val LOWER_TOP_RIGHT_SHAPE: VoxelShape = createCuboidShape(8.0, 0.0, 0.0, 16.0, 8.0, 8.0)
        val LOWER_TOP_LEFT_SHAPE: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 8.0, 8.0, 8.0)
        val LOWER_BOTTOM_RIGHT_SHAPE: VoxelShape = createCuboidShape(8.0, 0.0, 8.0, 16.0, 8.0, 16.0)
        val LOWER_BOTTOM_LEFT_SHAPE: VoxelShape = createCuboidShape(0.0, 0.0, 8.0, 8.0, 8.0, 16.0)

        private fun Direction.getOffset() = when (this) {
            Direction.UP -> Vec3d(0.0, -0.25, 0.0)
            Direction.DOWN -> Vec3d(0.0, 0.25, 0.0)
            Direction.NORTH -> Vec3d(0.0, 0.0, 0.25)
            Direction.SOUTH -> Vec3d(0.0, 0.0, -0.25)
            Direction.WEST -> Vec3d(0.25, 0.0, 0.0)
            Direction.EAST -> Vec3d(-0.25, 0.0, 0.0)
        }

        private val POS_TO_CORNER = mapOf(
            Vec3d(0.25, 0.25, 0.25) to LOWER_NORTH_WEST,
            Vec3d(0.75, 0.25, 0.25) to LOWER_NORTH_EAST,

            Vec3d(0.25, 0.25, 0.75) to LOWER_SOUTH_WEST,
            Vec3d(0.75, 0.25, 0.75) to LOWER_SOUTH_EAST,

            Vec3d(0.25, 0.75, 0.25) to UPPER_NORTH_WEST,
            Vec3d(0.75, 0.75, 0.25) to UPPER_NORTH_EAST,

            Vec3d(0.25, 0.75, 0.75) to UPPER_SOUTH_WEST,
            Vec3d(0.75, 0.75, 0.75) to UPPER_SOUTH_EAST,
        )
    }
}