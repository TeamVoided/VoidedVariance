package org.teamvoided.voided_variance.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.recipe.*
import net.minecraft.registry.HolderLookup
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import org.teamvoided.voided_variance.init.VVRecipeTypes
import java.util.*

class StrictShapedRecipe(
    group: String,
    category: CraftingCategory,
    val strictPattern: StrictShapedRecipePattern,
    val strictResult: ItemStack,
    showNotification: Boolean = true,
) : ShapedRecipe(
    group, category,
    ShapedRecipePattern(1, 1, DefaultedList.ofSize(1), Optional.empty()),
    strictResult, showNotification
) {
    override fun getSerializer() = VVRecipeTypes.STRICT_CRAFTING_SHAPED
    override fun getResult(provider: HolderLookup.Provider): ItemStack = this.strictResult
    override fun getIngredients(): DefaultedList<Ingredient> = this.strictPattern.ingredients
    override fun fits(width: Int, height: Int): Boolean =
        width >= this.strictPattern.width && height >= this.strictPattern.height

    override fun matches(craftingRecipeInput: CraftingRecipeInput, world: World): Boolean =
        this.strictPattern.matches(craftingRecipeInput)

    override fun craft(craftingRecipeInput: CraftingRecipeInput, provider: HolderLookup.Provider): ItemStack =
        this.getResult(provider).copy()

    override fun getWidth(): Int = this.strictPattern.width
    override fun getHeight(): Int = this.strictPattern.height


    override fun isEmpty(): Boolean {
        val list = ingredients
        return list.isEmpty() || list.stream()
            .filter { !it.isEmpty }
            .anyMatch { it.getMatchingStacks().size == 0 }
    }

    class Serializer : RecipeSerializer<StrictShapedRecipe> {
        override fun getCodec(): MapCodec<StrictShapedRecipe> = CODEC
        override fun getPacketCodec(): PacketCodec<RegistryByteBuf, StrictShapedRecipe> = PACKET_CODEC

        companion object {
            val CODEC: MapCodec<StrictShapedRecipe> = RecordCodecBuilder.mapCodec<StrictShapedRecipe> { instance ->
                instance.group<String, CraftingCategory, StrictShapedRecipePattern, ItemStack, Boolean>(
                    Codec.STRING.optionalFieldOf("group", "").forGetter<StrictShapedRecipe> { it.group },
                    CraftingCategory.CODEC.fieldOf("category").orElse(CraftingCategory.MISC)
                        .forGetter<StrictShapedRecipe> { it.category },
                    StrictShapedRecipePattern.CODEC.forGetter<StrictShapedRecipe> { it.strictPattern },
                    ItemStack.field_51397.fieldOf("result").forGetter<StrictShapedRecipe> { it.strictResult },
                    Codec.BOOL.optionalFieldOf("show_notification", true)
                        .forGetter<StrictShapedRecipe> { it.showNotification() }
                ).apply<StrictShapedRecipe>(instance, ::StrictShapedRecipe)
            }
            val PACKET_CODEC: PacketCodec<RegistryByteBuf, StrictShapedRecipe> = PacketCodec.create(::write, ::read)

            private fun read(buf: RegistryByteBuf): StrictShapedRecipe {
                val string = buf.readString()
                val craftingCategory = buf.readEnumConstant(CraftingCategory::class.java)
                val strictShapedRecipePattern = StrictShapedRecipePattern.PACKET_CODEC.decode(buf)
                val itemStack = ItemStack.PACKET_CODEC.decode(buf)
                val bl = buf.readBoolean()
                return StrictShapedRecipe(string, craftingCategory, strictShapedRecipePattern, itemStack, bl)
            }

            private fun write(buf: RegistryByteBuf, recipe: StrictShapedRecipe) {
                buf.writeString(recipe.group)
                buf.writeEnumConstant(recipe.category)
                StrictShapedRecipePattern.PACKET_CODEC.encode(buf, recipe.strictPattern)
                ItemStack.PACKET_CODEC.encode(buf, recipe.strictResult)
                buf.writeBoolean(recipe.showNotification())
            }
        }
    }
}

