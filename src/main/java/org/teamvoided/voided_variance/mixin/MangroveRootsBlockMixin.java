package org.teamvoided.voided_variance.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MangroveRootsBlock.class)
public class MangroveRootsBlockMixin extends Block implements SimpleWaterloggedBlock {

    @Unique
    private static final EnumProperty<Direction.Axis> dnd$AXIS = BlockStateProperties.AXIS;

    public MangroveRootsBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addDefaultState(Properties settings, CallbackInfo ci) {
        registerDefaultState(defaultBlockState().setValue(dnd$AXIS, Direction.Axis.Y));
    }

    @ModifyReturnValue(method = "skipRendering", at = @At("RETURN"))
    public boolean addDirectionality(boolean original, BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.is(Blocks.MANGROVE_ROOTS) && direction.getAxis() == state.getValue(dnd$AXIS);
    }

    @ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
    public BlockState addDirectionalPlacement(BlockState original, BlockPlaceContext ctx) {
        if (original == null) {
            return null;
        }
        return original.setValue(dnd$AXIS, ctx.getClickedFace().getAxis());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return RotatedPillarBlock.rotatePillar(state, rotation);
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    public void addDirectionalSideInvisible(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(dnd$AXIS);
    }

}