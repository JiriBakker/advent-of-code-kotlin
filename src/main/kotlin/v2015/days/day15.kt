package v2015.days.day15

import util.combine
import kotlin.math.max

private class Ingredient(val name: String, val capacity: Long, val durability: Long, val flavor: Long, val texture: Long, val calories: Long)

private fun parseIngredients(input: List<String>): List<Ingredient> {
    return input.map {
        val segments = it.split(" ")
        val name = segments[0].trimEnd(':')
        val capacity = segments[2].trimEnd(',').toLong()
        val durability = segments[4].trimEnd(',').toLong()
        val flavor = segments[6].trimEnd(',').toLong()
        val texture = segments[8].trimEnd(',').toLong()
        val calories = segments[10].toLong()
        Ingredient(name, capacity, durability, flavor, texture, calories)
    }
}

private fun generateValidCombinations(count: Int, max: Int, prefix: List<Int> = listOf()): List<List<Int>> {
    if (count == 1) {
        return listOf(prefix + max)
    }
    return IntRange(0, max).flatMap {
        generateValidCombinations(count - 1, max - it, prefix + it)
    }
}

private fun computeRecipeScores(ingredients: List<Ingredient>): List<Pair<Long, Long>> {
    return generateValidCombinations(ingredients.size, 100)
        .map { combination ->
            val capacitySum = max(0, ingredients.mapIndexed { index, ingredient -> ingredient.capacity * combination[index] }.sum())
            val durabilitySum = max(0, ingredients.mapIndexed { index, ingredient -> ingredient.durability * combination[index] }.sum())
            val flavorSum = max(0, ingredients.mapIndexed { index, ingredient -> ingredient.flavor * combination[index] }.sum())
            val textureSum = max(0, ingredients.mapIndexed { index, ingredient -> ingredient.texture * combination[index] }.sum())
            val calorieSum = ingredients.mapIndexed { index, ingredient -> ingredient.calories * combination[index] }.sum()
            capacitySum * durabilitySum * flavorSum * textureSum to calorieSum
        }
}

fun day15a(input: List<String>): Long {
    val ingredients = parseIngredients(input)

    return computeRecipeScores(ingredients)
        .maxBy { it.first }
        .let { it!!.first }
}

fun day15b(input: List<String>): Long {
    val ingredients = parseIngredients(input)

    return computeRecipeScores(ingredients)
        .filter { it.second == 500L }
        .maxBy { it.first }
        .let { it!!.first }
}