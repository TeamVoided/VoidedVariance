package org.teamvoided.voided_variance.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level
import org.teamvoided.voided_variance.init.VVRecipeTypes
import java.util.*

class StrictShapedRecipe(
    group: String,
    category: CraftingBookCategory,
    val strictPattern: StrictShapedRecipePattern,
    val strictResult: ItemStack,
    showNotification: Boolean = true,
) : ShapedRecipe(
    group, category,
    ShapedRecipePattern(1, 1, NonNullList.createWithCapacity(1), Optional.empty()),
    strictResult, showNotification
) {

    override fun getSerializer() = VVRecipeTypes.STRICT_CRAFTING_SHAPED

    override fun getResultItem(lookup: HolderLookup.Provider): ItemStack = strictResult

    override fun getIngredients(): NonNullList<Ingredient> = strictPattern.ingredients

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return width >= getWidth() && height >= getHeight()
    }

    override fun matches(input: CraftingInput, world: Level): Boolean {
        return strictPattern.matches(input)
    }

    override fun assemble(input: CraftingInput, lookup: HolderLookup.Provider): ItemStack {
        return getResultItem(lookup).copy()
    }

    override fun getWidth(): Int = strictPattern.width

    override fun getHeight(): Int = strictPattern.height

    override fun isIncomplete(): Boolean {
        return ingredients.isEmpty() || ingredients.stream().filter { !it.isEmpty }.anyMatch { it.getItems().size == 0 }
    }

    class Serializer : RecipeSerializer<StrictShapedRecipe> {

        override fun codec(): MapCodec<StrictShapedRecipe> = CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, StrictShapedRecipe> = PACKET_CODEC

        companion object {
            val CODEC: MapCodec<StrictShapedRecipe> = RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter { it.group },
                    CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC)
                        .forGetter { it.category() },
                    StrictShapedRecipePattern.CODEC.forGetter { it.strictPattern },
                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter { it.strictResult },
                    Codec.BOOL.optionalFieldOf("show_notification", true)
                        .forGetter { it.showNotification() }
                ).apply(instance, ::StrictShapedRecipe)
            }
            val PACKET_CODEC: StreamCodec<RegistryFriendlyByteBuf, StrictShapedRecipe> = StreamCodec.of(::write, ::read)

            private fun read(buf: RegistryFriendlyByteBuf): StrictShapedRecipe {
                val string = buf.readUtf()
                val craftingCategory = buf.readEnum(CraftingBookCategory::class.java)
                val strictShapedRecipePattern = StrictShapedRecipePattern.PACKET_CODEC.decode(buf)
                val itemStack = ItemStack.STREAM_CODEC.decode(buf)
                val showNotification = buf.readBoolean()
                return StrictShapedRecipe(string, craftingCategory, strictShapedRecipePattern, itemStack, showNotification)
            }

            private fun write(buf: RegistryFriendlyByteBuf, recipe: StrictShapedRecipe) {
                buf.writeUtf(recipe.group)
                buf.writeEnum(recipe.category())
                StrictShapedRecipePattern.PACKET_CODEC.encode(buf, recipe.strictPattern)
                ItemStack.STREAM_CODEC.encode(buf, recipe.strictResult)
                buf.writeBoolean(recipe.showNotification())
            }
        }
    }
}

