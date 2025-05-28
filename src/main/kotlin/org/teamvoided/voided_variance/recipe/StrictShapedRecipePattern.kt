package org.teamvoided.voided_variance.recipe

import com.google.common.annotations.VisibleForTesting
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import it.unimi.dsi.fastutil.chars.CharArraySet
import it.unimi.dsi.fastutil.chars.CharSet
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.ValueFirstEncoder
import net.minecraft.recipe.CraftingRecipeInput
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Util
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.dynamic.Codecs
import java.util.*
import java.util.function.Function
import kotlin.math.max
import kotlin.math.min

class StrictShapedRecipePattern(
    val width: Int, val height: Int,
    val ingredients: DefaultedList<Ingredient>,
    val groups: DefaultedList<Char>,
    private val data: Optional<ShapeData>,
) {
    private val ingredientCount: Int
    private val symmetric: Boolean

    init {
        var i = 0

        for (ingredient in ingredients) {
            if (!ingredient.isEmpty) {
                i++
            }
        }

        this.ingredientCount = i
        this.symmetric = Util.isHorizontallySymmetric<Ingredient>(width, height, ingredients)
    }

    fun matches(input: CraftingRecipeInput): Boolean {
        if (input.inputCount != this.ingredientCount) {
            return false
        } else {
            if (input.width == this.width && input.height == this.height) {
                if (!this.symmetric && this.matches(input, true)) {
                    return true
                }

                if (this.matches(input, false)) {
                    return true
                }
            }

            return false
        }
    }

    private fun matches(input: CraftingRecipeInput, mirror: Boolean): Boolean {
        val groups = mutableMapOf<Char, List<ItemStack>>()
        for (i in 0..<this.height) {
            for (j in 0..<this.width) {
                val ingredient: Ingredient =
                    if (mirror) this.ingredients[this.width - j - 1 + i * this.width]
                    else this.ingredients[j + i * this.width]

                val itemStack = input.get(j, i)
                if (!ingredient.test(itemStack)) {
                    return false
                }
                val group: Char =
                    if (mirror) this.groups[this.width - j - 1 + i * this.width]
                    else this.groups[j + i * this.width]
                if (group != EMPTY_CHAR) {
                    groups[group] = (groups[group] ?: listOf()) + listOf(itemStack)
                }
            }
        }
        for ((_, stacks) in groups) {
            if (stacks.size <= 1) continue
            var uniform = true
            for (stack in stacks) {
                if (!stack.isOf(stacks[0].item)) {
                    uniform = false
                    break
                }
            }
            if (uniform) return false
        }

        return true
    }

    private fun toBuf(buf: RegistryByteBuf) {
        buf.writeVarInt(this.width)
        buf.writeVarInt(this.height)
        for (group in this.groups) {
            buf.writeChar(group?.code ?: EMPTY_CHAR.code)
        }
        for (ingredient in this.ingredients) {
            Ingredient.PACKET_CODEC.encode(buf, ingredient)
        }
    }

    @JvmRecord
    data class ShapeData(val key: MutableMap<Char, Pair<Boolean, Ingredient>>, val pattern: MutableList<String>) {
        companion object {
            private val PATTERN_CODEC: Codec<MutableList<String>> = Codec.STRING.listOf()
                .comapFlatMap(
                    { pattern ->
                        if (pattern.size > MAX_WIDTH_AND_HEIGHT) {
                            return@comapFlatMap DataResult.error { "Invalid pattern: too many rows, 3 is maximum" }
                        } else if (pattern.isEmpty()) {
                            return@comapFlatMap DataResult.error { "Invalid pattern: empty pattern not allowed" }
                        } else {
                            val firstLen: Int = pattern.first().length
                            for (string in pattern) {
                                if (string.length > MAX_WIDTH_AND_HEIGHT) {
                                    return@comapFlatMap DataResult.error { "Invalid pattern: too many columns, 3 is maximum" }
                                }

                                if (firstLen != string.length) {
                                    return@comapFlatMap DataResult.error { "Invalid pattern: each row must be the same width" }
                                }
                            }
                            return@comapFlatMap DataResult.success(pattern)
                        }
                    }, Function.identity()
                )
            private val KEY_SYMBOL_CODEC: Codec<Char> = Codec.STRING.comapFlatMap<Char>({ symbol ->
                if (symbol.length != 1) {
                    return@comapFlatMap DataResult.error { "Invalid key entry: '$symbol' is an invalid symbol (must be 1 character only)." }
                } else {
                    return@comapFlatMap if (" " == symbol) DataResult.error { "Invalid key entry: ' ' is a reserved symbol." }
                    else DataResult.success<Char>(symbol[0])
                }
            }, { it.toString() })

            val INTERNAL_PAIR_CODEC = Codec.pair(
                Codec.BOOL.fieldOf("strict").codec(),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").codec()
            )
            val PAIR_CODEC: Codec<Pair<Boolean, Ingredient>> = object : Codec<Pair<Boolean, Ingredient>> {
                override fun <T : Any> encode(
                    input: Pair<Boolean, Ingredient>,
                    ops: DynamicOps<T>,
                    prefix: T,
                ): DataResult<T> {
                    return if (input.first) {
                        INTERNAL_PAIR_CODEC.encode(input, ops, prefix)
                    } else {
                        val result = Ingredient.DISALLOW_EMPTY_CODEC.encode(input.second, ops, prefix)
                        if (result.isSuccess) result
                        else result.error().get()
                    }
                }

                override fun <T : Any> decode(
                    ops: DynamicOps<T>,
                    input: T,
                ): DataResult<Pair<Pair<Boolean, Ingredient>, T>> {
                    return if (ops.get(input, "strict").isSuccess) {
                        INTERNAL_PAIR_CODEC.decode(ops, input)
                    } else {
                        val result = Ingredient.DISALLOW_EMPTY_CODEC.decode(ops, input)
                        if (result.isSuccess)
                            DataResult.success(Pair.of(Pair.of(false, result.result().get().first), input))
                        else
                            DataResult.error { result.error().get().message() }
                    }
                }
            }


            val CODEC: MapCodec<ShapeData> = RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Codecs.createStrictUnboundedMap(
                        KEY_SYMBOL_CODEC, PAIR_CODEC
                    ).fieldOf("key").forGetter { it.key },
                    PATTERN_CODEC.fieldOf("pattern").forGetter<ShapeData> { it.pattern }
                ).apply(instance, ::ShapeData)
            }
        }
    }

    companion object {
        private const val MAX_WIDTH_AND_HEIGHT = 3
        const val EMPTY_CHAR = ' '
        val CODEC: MapCodec<StrictShapedRecipePattern> = ShapeData.Companion.CODEC.flatXmap(::fromData) { pattern ->
            pattern.data
                .map { DataResult.success(it) }
                .orElseGet { DataResult.error { "Cannot encode unpacked recipe" } }
        }
        val PACKET_CODEC: PacketCodec<RegistryByteBuf, StrictShapedRecipePattern> =
            PacketCodec.create<RegistryByteBuf, StrictShapedRecipePattern>(
                ValueFirstEncoder { obj, buf -> obj.toBuf(buf) }, ::fromBuf
            )

        fun of(key: MutableMap<Char, Pair<Boolean, Ingredient>>, vararg pattern: String): StrictShapedRecipePattern {
            val data = ShapeData(key, pattern.toMutableList())
            return fromData(data).getOrThrow()
        }

        private fun fromData(data: ShapeData): DataResult<StrictShapedRecipePattern> {
            val strings = trim(data.pattern)
            val i = strings.getOrNull(0)?.length ?: 0
            val j = strings.size
            val defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY)
            val groupList: DefaultedList<Char> = DefaultedList.ofSize(i * j, EMPTY_CHAR)
            val charSet: CharSet = CharArraySet(data.key.keys)

            for (k in strings.indices) {
                val string = strings[k]

                for (l in 0..<string.length) {
                    val c = string[l]
                    val ingredient = if (c == EMPTY_CHAR) Ingredient.EMPTY else data.key[c]?.second
                    if (ingredient == null) {
                        return DataResult.error { "Pattern references symbol '$c' but it's not defined in the key" }
                    }

                    charSet.remove(c)
                    defaultedList[l + i * k] = ingredient
                    if (data.key[c]?.first == true) {
                        groupList[l + i * k] = c
                    }
                }
            }

            return if (!charSet.isEmpty())
                DataResult.error { "Key defines symbols that aren't used in pattern: $charSet" }
            else
                DataResult.success(StrictShapedRecipePattern(i, j, defaultedList, groupList, Optional.of(data)))
        }

        @VisibleForTesting
        fun trim(pattern: MutableList<String>): Array<String> {
            var i = Int.Companion.MAX_VALUE
            var j = 0
            var k = 0
            var l = 0

            for (m in 0 until pattern.size) {
                val string = pattern[m]
                i = min(i, findFirstSymbol(string))
                val n = findLastSymbol(string)
                j = max(j, n)
                if (n < 0) {
                    if (k == m) k++
                    l++
                } else l = 0
            }
            if (pattern.size == l) {
                return arrayOf()
            }
            val strings = mutableListOf<String>()

            for (o in 0 until pattern.size - l - k) {
                strings.add(pattern[o + k].substring(i, j + 1))
            }
            return strings.toTypedArray()
        }

        fun findFirstSymbol(row: String): Int {
            var i = 0
            while (i < row.length && row[i] == EMPTY_CHAR) {
                i++
            }
            return i
        }

        fun findLastSymbol(row: String): Int {
            var i = row.length - 1
            while (i >= 0 && row[i] == EMPTY_CHAR) {
                i--
            }
            return i
        }

        private fun fromBuf(buf: RegistryByteBuf): StrictShapedRecipePattern {
            val i = buf.readVarInt()
            val j = buf.readVarInt()

            val groupList: DefaultedList<Char> = DefaultedList.ofSize(i * j, EMPTY_CHAR)
            groupList.replaceAll { buf.readChar() }

            val defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY)
            defaultedList.replaceAll { ingredient: Ingredient -> Ingredient.PACKET_CODEC.decode(buf) }
            return StrictShapedRecipePattern(i, j, defaultedList, groupList, Optional.empty())
        }
    }
}
