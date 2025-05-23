package org.teamvoided.voided_variance.init

import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.Registries
import org.teamvoided.voided_variance.VoidedVariance.id
import org.teamvoided.voided_variance.recipe.StrictShapedRecipe
import org.teamvoided.voided_variance.utils.register

object VVRecipeTypes {

    val STRICT_CRAFTING_SHAPED = serializer("strict_crafting_shaped", StrictShapedRecipe.Serializer())

    fun init() = Unit

    fun <T : Recipe<*>> serializer(id: String, serializer: RecipeSerializer<T>): RecipeSerializer<T> {
        Registries.RECIPE_SERIALIZER.register(id(id), serializer)
        return serializer
    }

    fun <T : Recipe<*>> type(id: String, type: RecipeType<T>): RecipeType<T> {
        Registries.RECIPE_TYPE.register(id(id), type)
        return type
    }

}