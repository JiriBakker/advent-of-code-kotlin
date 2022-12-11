import util.splitByDoubleNewLine
import Day11.Monkey
import util.productOf
import java.math.BigDecimal

fun day11a(input: List<String>): Long {
    val monkeys = mutableListOf<Monkey>()
    input.splitByDoubleNewLine().forEach { lines ->
        val startingItems = lines[1].drop(18).split(", ").map { it.trim().toBigDecimal() }
        val operationParts = lines[2].drop(23).split(" ")
        val operationOperator: (BigDecimal, BigDecimal) -> BigDecimal =
            if (operationParts[0] == "*") BigDecimal::times
            else BigDecimal::plus

        val operation: (BigDecimal) -> BigDecimal =
            if (operationParts[1] == "old") { old -> operationOperator.invoke(old, old) }
            else { old -> operationOperator.invoke(old, operationParts[1].toBigDecimal()) }

        val testDivisor = lines[3].drop(21).trim().toBigDecimal()
        val test = { new: BigDecimal ->
            val monkeyTrueIndex = lines[4].drop(29).trim().toInt()
            val monkeyFalseIndex = lines[5].drop(30).trim().toInt()
            val monkeyToThrowTo =
                if (new.remainder(testDivisor).compareTo(BigDecimal.ZERO) == 0) monkeys[monkeyTrueIndex]
                else monkeys[monkeyFalseIndex]

            monkeyToThrowTo
        }

        monkeys.add(
            Monkey(
                startingItems,
                operation,
                test
            )
        )
    }

    repeat(20) {
        monkeys.forEach { it.inspectItems(BigDecimal.ONE) }
    }

    return monkeys.sortedByDescending { it.inspectCount }.take(2).productOf { it.inspectCount }

}

fun day11b(input: List<String>): Long {val monkeys = mutableListOf<Monkey>()
    var trimMaximum = BigDecimal.ONE

    input.splitByDoubleNewLine().forEach { lines ->
        val startingItems = lines[1].drop(18).split(", ").map { it.trim().toBigDecimal() }
        val operationParts = lines[2].drop(23).split(" ")
        val operationOperator: (BigDecimal, BigDecimal) -> BigDecimal =
            if (operationParts[0] == "*") BigDecimal::times
            else BigDecimal::plus

        val operation: (BigDecimal) -> BigDecimal =
            if (operationParts[1] == "old") { old -> operationOperator.invoke(old, old) }
            else { old -> operationOperator.invoke(old, operationParts[1].toBigDecimal()) }

        val testDivisor = lines[3].drop(21).trim().toBigDecimal()

        trimMaximum *= testDivisor

        val test = { new: BigDecimal ->
            val monkeyTrueIndex = lines[4].drop(29).trim().toInt()
            val monkeyFalseIndex = lines[5].drop(30).trim().toInt()
            val monkeyToThrowTo =
                if (new.remainder(testDivisor).compareTo(BigDecimal.ZERO) == 0) monkeys[monkeyTrueIndex]
                else monkeys[monkeyFalseIndex]

            monkeyToThrowTo
        }

        monkeys.add(
            Monkey(
                startingItems,
                operation,
                test,
                trimFunc = { worryLevel -> worryLevel.remainder(trimMaximum) }
            )
        )
    }

    repeat(20) {
        monkeys.forEach { it.inspectItems(trimMaximum) }
    }
    repeat(980) {
        monkeys.forEach { it.inspectItems(trimMaximum) }
    }
    repeat(9000) {
        monkeys.forEach { it.inspectItems(trimMaximum) }
    }

    return monkeys.sortedByDescending { it.inspectCount }.take(2).productOf { it.inspectCount }

}

object Day11 {
    class Monkey(
        startingItems: List<BigDecimal>,
        val operationFunc: (BigDecimal) -> BigDecimal,
        val testFunc: (BigDecimal) -> Monkey,
        val trimFunc: (BigDecimal) -> BigDecimal = { it / BigDecimal.valueOf(3) }
    ) {
        val items = startingItems.toMutableList()

        var inspectCount = 0L

        fun catchItem(item: BigDecimal) {
            items.add(item)
        }

        fun inspectItems(trimDivisor: BigDecimal) {
            items.forEach { item ->
                val worryLevelAfterOperation = operationFunc(item)
                 val trimmedWorryLevel =
                     if (worryLevelAfterOperation > trimDivisor * trimDivisor) worryLevelAfterOperation.remainder(trimDivisor * trimDivisor)
                    else worryLevelAfterOperation
                val worryLevelAfterBored = trimFunc.invoke(trimmedWorryLevel)
                val monkeyToThrowTo = testFunc(worryLevelAfterBored)
                monkeyToThrowTo.catchItem(worryLevelAfterBored)
                inspectCount++
            }
            items.clear()
        }
    }

}