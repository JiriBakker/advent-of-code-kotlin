import util.findKeyForMaxValue
import util.inc

private typealias Ingredient = String
private typealias Allergen = String

private typealias IngredientOccurrences = Map<Ingredient, Int>
private typealias VerifiedAllergens = Map<Allergen, Ingredient>

private fun deduceAllergens(input: List<String>): Pair<IngredientOccurrences, VerifiedAllergens> {
    val possibleIngredients = mutableMapOf<Allergen, MutableMap<Ingredient, Int>>()
    val ingredientOccurrences = mutableMapOf<Ingredient, Int>()

    input.forEach { line ->
        val (ingredientList, allergenList) = line.split(" (")
        val allergens = allergenList.dropLast(1).split(", ")
        val ingredients = ingredientList.split(" ")

        ingredients.forEach { ingredient -> ingredientOccurrences.inc(ingredient) }

        allergens.forEach { allergenWithPrefix ->
            val allergen = allergenWithPrefix.replace("contains ", "")
            possibleIngredients.getOrPut(allergen) { mutableMapOf() }
                .let {
                    ingredients.forEach { ingredient -> it.inc(ingredient) }
                }
        }
    }

    val verifiedAllergens = mutableMapOf<Allergen, Ingredient>()

    while (true) {
        val match =
            possibleIngredients.entries.asSequence()
                .map { (allergen, ingredients) -> allergen to ingredients.findKeyForMaxValue() }
                .firstOrNull { it.second != null }

        if (match == null) {
            break
        }
        val (solvedAllergen, solvedIngredient) = match
        verifiedAllergens[solvedAllergen] = solvedIngredient!!

        possibleIngredients.remove(solvedAllergen)
        possibleIngredients.entries.forEach { (_, ingredients) -> ingredients.remove(solvedIngredient) }
    }

    return ingredientOccurrences to verifiedAllergens
}

fun day21a(input: List<String>): Int {
    val (ingredientOccurrences, verifiedAllergens) = deduceAllergens(input)
    val unknownIngredients = ingredientOccurrences.entries.filter { (ingredient, _) -> !verifiedAllergens.containsValue(ingredient) }
    return unknownIngredients.sumOf { it.value }
}

fun day21b(input: List<String>): String {
    val (_, verifiedAllergens) = deduceAllergens(input)
    return verifiedAllergens.toSortedMap().values.joinToString(",")
}