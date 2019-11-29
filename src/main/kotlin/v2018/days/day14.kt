package v2018.days.day14

private fun iterateRecipes(recipes: MutableList<Int>, loopCondition: () -> Boolean, addRecipeAndMaybeReturn: (Int) -> Boolean) {
    fun move(curIndex: Int, curRecipe: Int): Int {
        return (curIndex + curRecipe + 1) % recipes.size
    }

    var firstElfIndex = 0
    var secondElfIndex = 1
    while (loopCondition()) {
        val firstRecipe = recipes[firstElfIndex]
        val secondRecipe = recipes[secondElfIndex]
        val sum = firstRecipe + secondRecipe

        if (sum >= 10 && addRecipeAndMaybeReturn(sum / 10)) {
            return
        }
        if (addRecipeAndMaybeReturn(sum % 10)) {
            return
        }

        firstElfIndex = move(firstElfIndex, firstRecipe)
        secondElfIndex = move(secondElfIndex, secondRecipe)
    }
}

fun day14a(input: String): String {
    val maxNrOfRecipes = input.toInt()
    val recipes = mutableListOf(3, 7)

    iterateRecipes(
        recipes,
        { recipes.size < maxNrOfRecipes + 10 },
        { recipes.add(it); false }
    )

    return recipes.subList(maxNrOfRecipes, maxNrOfRecipes + 10).joinToString("")
}

fun day14b(input: String): Int? {
    val digitsToFind = input.map(Character::getNumericValue)

    val recipes = mutableListOf(3, 7)

    var matchCount = 0
    fun hasRecipeBeenFound(digit: Int): Boolean {
        if (digitsToFind[matchCount] != digit) {
            matchCount = 0
        }
        if (digitsToFind[matchCount] == digit) {
            matchCount++
        }
        return matchCount == digitsToFind.size
    }

    iterateRecipes(
        recipes,
        { true },
        { recipes.add(it); hasRecipeBeenFound(it) }
    )

    return recipes.size - digitsToFind.size
}
