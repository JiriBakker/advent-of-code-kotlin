package days.day14

private fun iterateRecipes(recipes: MutableList<Int>, loopCondition: () -> Boolean, addRecipeAndMaybeReturn: (Int) -> Boolean) {
    fun move(curIndex: Int): Int {
        return (curIndex + recipes[curIndex] + 1) % recipes.size
    }

    var firstElfIndex = 0
    var secondElfIndex = 1
    while (loopCondition()) {
        val sum = recipes[firstElfIndex] + recipes[secondElfIndex]

        if (sum >= 10 && addRecipeAndMaybeReturn(sum / 10)) {
            return
        }
        if (addRecipeAndMaybeReturn(sum % 10)) {
            return
        }

        firstElfIndex = move(firstElfIndex)
        secondElfIndex = move(secondElfIndex)
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
    val digitsToFind = input.map(Character::getNumericValue).reversed()
    val recipes = mutableListOf(3, 7)

    fun insertAndCheckIfAnswerFound(digit: Int): Boolean {
        recipes.add(digit)
        for (index in 0 until digitsToFind.size) {
            if (recipes[recipes.size - 1 - index] != digitsToFind[index]) {
                return false
            }
        }
        return true
    }

    iterateRecipes(
        recipes,
        { true },
        { insertAndCheckIfAnswerFound(it) }
    )

    return recipes.size - digitsToFind.size
}
