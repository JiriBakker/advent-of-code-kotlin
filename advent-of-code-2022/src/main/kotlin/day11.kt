import util.splitByDoubleNewLine
import Day11.parseMonkeys
import Day11.runRounds
import Day11.computeMonkeyBusiness
import util.isDivisibleBy
import util.productOfLong
import java.math.BigInteger

fun day11a(input: List<String>) =
    input
        .parseMonkeys(BigInteger.valueOf(3))
        .runRounds(20)
        .computeMonkeyBusiness()

fun day11b(input: List<String>) =
    input
        .parseMonkeys(BigInteger.valueOf(1))
        .runRounds(10000)
        .computeMonkeyBusiness()

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
                    if (new.isDivisibleBy(testDivisor)) monkeys[monkeyTrueIndex]
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

    fun List<Monkey>.runRounds(nrOfRounds: Int) =
        this.apply {
            repeat(nrOfRounds) {
                this.forEach { it.inspectItems() }
            }
        }

    class Monkey(
        startingItems: List<BigInteger>,
        private val operationFunc: (BigInteger) -> BigInteger,
        private val testFunc: (BigInteger) -> Monkey,
        private val boredDivisor: BigInteger,
        private val trimFunc: (BigInteger) -> BigInteger
    ) {
        private val worryLevels = startingItems.toMutableList()

        var inspectCount = 0L
            private set

        private fun catchItem(worryLevel: BigInteger) {
            worryLevels.add(worryLevel)
        }

        fun inspectItems() {
            worryLevels.forEach { worryLevel ->
                val worryLevelAfterOperation = operationFunc(worryLevel)
                val boredWorryLevel = worryLevelAfterOperation / boredDivisor
                val trimmedWorryLevel = trimFunc.invoke(boredWorryLevel)

                val monkeyToThrowTo = testFunc(trimmedWorryLevel)
                monkeyToThrowTo.catchItem(trimmedWorryLevel)
                inspectCount++
            }
            worryLevels.clear()
        }
    }

    fun List<Monkey>.computeMonkeyBusiness() =
        this.sortedByDescending { it.inspectCount }
            .take(2)
            .productOfLong { it.inspectCount }

}