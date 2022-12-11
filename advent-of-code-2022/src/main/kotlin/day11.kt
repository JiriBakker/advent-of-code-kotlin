import util.splitByDoubleNewLine
import Day11.parseMonkeys
import Day11.sumTopTwoInspectCounts
import util.productOf
import java.math.BigInteger

fun day11a(input: List<String>): Long {
 val monkeys = input.parseMonkeys(BigInteger.valueOf(3))

    repeat(20) {
        monkeys.forEach { it.inspectItems() }
    }

    return monkeys.sumTopTwoInspectCounts()
}

fun day11b(input: List<String>): Long {
    val monkeys = input.parseMonkeys(BigInteger.ONE)

    repeat(10000) {
        monkeys.forEach { it.inspectItems() }
    }

    return monkeys.sumTopTwoInspectCounts()
}

object Day11 {

    fun List<String>.parseMonkeys(boredDivisor: BigInteger): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()

        var divisorProduct = BigInteger.ONE
        this.splitByDoubleNewLine().forEach { lines ->
            val startingItems = lines[1].drop(18).split(", ").map { it.trim().toBigInteger() }
            val operationParts = lines[2].drop(23).split(" ")
            val operationOperator: (BigInteger, BigInteger) -> BigInteger =
                if (operationParts[0] == "*") BigInteger::times
                else BigInteger::plus

            val operation: (BigInteger) -> BigInteger =
                if (operationParts[1] == "old") { old -> operationOperator.invoke(old, old) }
                else { old -> operationOperator.invoke(old, operationParts[1].toBigInteger()) }

            val testDivisor = lines[3].drop(21).trim().toBigInteger()

            divisorProduct *= testDivisor

            val test = { new: BigInteger ->
                val monkeyTrueIndex = lines[4].drop(29).trim().toInt()
                val monkeyFalseIndex = lines[5].drop(30).trim().toInt()
                val monkeyToThrowTo =
                    if (new.remainder(testDivisor).compareTo(BigInteger.ZERO) == 0) monkeys[monkeyTrueIndex]
                    else monkeys[monkeyFalseIndex]

                monkeyToThrowTo
            }

            monkeys.add(
                Monkey(
                    startingItems,
                    operation,
                    test,
                    boredDivisor,
                    trimFunc = { worryLevel -> worryLevel % (divisorProduct * divisorProduct) }
                )
            )
        }

        return monkeys
    }

    class Monkey(
        startingItems: List<BigInteger>,
        val operationFunc: (BigInteger) -> BigInteger,
        val testFunc: (BigInteger) -> Monkey,
        val boredDivisor: BigInteger,
        val trimFunc: (BigInteger) -> BigInteger
    ) {
        private val items = startingItems.toMutableList()

        var inspectCount = 0L

        private fun catchItem(item: BigInteger) {
            items.add(item)
        }

        fun inspectItems() {
            items.forEach { item ->
                val worryLevelAfterOperation = operationFunc(item)
                val boredWorryLevel = worryLevelAfterOperation / boredDivisor
                val trimmedWorryLevel = trimFunc.invoke(boredWorryLevel)
                val monkeyToThrowTo = testFunc(trimmedWorryLevel)
                monkeyToThrowTo.catchItem(trimmedWorryLevel)
                inspectCount++
            }
            items.clear()
        }
    }

    fun List<Monkey>.sumTopTwoInspectCounts() =
        this.sortedByDescending { it.inspectCount }
            .take(2)
            .productOf { it.inspectCount }

}