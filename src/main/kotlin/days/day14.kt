package days.day14

private class Recipe(val digit: Int) {
    var prev: Recipe = this
        private set

    var next: Recipe = this
        private set

    fun insertSelfBefore(other: Recipe) {
        this.next = other
        this.prev = other.prev
        other.prev.next = this
        other.prev = this
    }
}

private fun String.toDigits(): List<Int> {
    return this.map(Character::getNumericValue)
}

private fun moveElf(startRecipe: Recipe): Recipe {
    var currentRecipe = startRecipe
    val steps = currentRecipe.digit + 1
    repeat(steps) { currentRecipe = currentRecipe.next }
    return currentRecipe
}

private fun print(firstRecipe: Recipe, firstElfRecipe: Recipe, secondElfRecipe: Recipe) {
    var currentRecipe = firstRecipe
    do {
        if (currentRecipe == firstElfRecipe && currentRecipe == secondElfRecipe) {
            print("X${currentRecipe.digit}X")
        } else if (currentRecipe == firstElfRecipe) {
            print("(${currentRecipe.digit})")
        } else if (currentRecipe == secondElfRecipe) {
            print("[${currentRecipe.digit}]")
        } else {
            print("${currentRecipe.digit}".padStart(2).padEnd(3))
        }
        currentRecipe = currentRecipe.next
    } while (currentRecipe != firstRecipe)
    println()
}

fun day14a(input: String): String {
    val maxNrOfRecipes = input.toInt() + 10

    var recipeCount = 0
    fun createRecipe(digit: Int): Recipe {
        recipeCount++
        return Recipe(digit)
    }

    val firstRecipe = createRecipe(3)
    val secondRecipe = createRecipe(7)
    secondRecipe.insertSelfBefore(firstRecipe)

    var firstElfRecipe = firstRecipe
    var secondElfRecipe = secondRecipe

    while (recipeCount < maxNrOfRecipes) {
        val sum = firstElfRecipe.digit + secondElfRecipe.digit
        if (sum >= 10) {
            createRecipe(sum / 10).insertSelfBefore(firstRecipe)
        }
        createRecipe(sum % 10).insertSelfBefore(firstRecipe)

        firstElfRecipe = moveElf(firstElfRecipe)
        secondElfRecipe = moveElf(secondElfRecipe)
    }

    var answerCurrentRecipe = firstRecipe
    repeat(recipeCount - maxNrOfRecipes + 10) {
        answerCurrentRecipe = answerCurrentRecipe.prev
    }
    val answer = StringBuilder()
    repeat(10) {
        answer.append(answerCurrentRecipe.digit)
        answerCurrentRecipe = answerCurrentRecipe.next
    }
    return answer.toString()
}

fun day14b(input: String): Int? {
    val digitsToFind = input.toDigits().reversed()

    var recipeCount = 0
    fun createRecipe(digit: Int): Recipe {
        recipeCount++
        return Recipe(digit)
    }

    val firstRecipe = createRecipe(3)
    val secondRecipe = createRecipe(7)
    secondRecipe.insertSelfBefore(firstRecipe)

    fun isAnswerFound(): Boolean {
        digitsToFind.fold(firstRecipe.prev) { recipe, digit ->
            if (recipe.digit != digit) {
                return false
            }
            recipe.prev
        }
        return true
    }

    fun insertAndCheckIfAnswerFound(digit: Int): Boolean {
        createRecipe(digit).insertSelfBefore(firstRecipe)
        return digit == digitsToFind.first() && isAnswerFound()
    }

    var firstElfRecipe = firstRecipe
    var secondElfRecipe = secondRecipe

    while (true) {
        val sum = firstElfRecipe.digit + secondElfRecipe.digit
        if (
            (sum >= 10 && insertAndCheckIfAnswerFound(sum / 10)) ||
            insertAndCheckIfAnswerFound(sum % 10)
        ) {
            return recipeCount - digitsToFind.size
        }

        firstElfRecipe = moveElf(firstElfRecipe)
        secondElfRecipe = moveElf(secondElfRecipe)
    }
}
