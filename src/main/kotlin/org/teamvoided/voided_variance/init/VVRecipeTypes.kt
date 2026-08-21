package org.teamvoided.voided_variance.init

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.recipe.StrictShapedRecipe
import org.teamvoided.voided_variance.utils.register

object VVRecipeTypes {

    val STRICT_CRAFTING_SHAPED = serializer("strict_crafting_shaped", StrictShapedRecipe.Serializer())

    fun init() = Unit

    fun <T : Recipe<*>> serializer(id: String, serializer: RecipeSerializer<T>): RecipeSerializer<T> {
        BuiltInRegistries.RECIPE_SERIALIZER.register(id(id), serializer)
        return serializer
    }

    fun <T : Recipe<*>> type(id: String, type: RecipeType<T>): RecipeType<T> {
        BuiltInRegistries.RECIPE_TYPE.register(id(id), type)
        return type
    }

}