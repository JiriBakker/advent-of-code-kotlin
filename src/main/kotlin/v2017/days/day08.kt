package v2017.days.day08

import util.component6
import util.component7
import util.gt
import util.gte
import util.lt
import util.lte
import util.notEquals
import kotlin.math.max

private fun applyInstructions(instructions: List<String>, valueProcessor: (Int) -> Unit = {}): Map<String, Int> {
    val registers = mutableMapOf<String, Int>()

    fun updateRegister(key: String, delta: Int) {
        val value = (registers[key] ?: 0) + delta
        registers[key] = value
        valueProcessor(value)
    }

    instructions.forEach { line ->
        val (destIndex, operation, deltaValue, _, sourceIndex, operator, compareValue) = line.split(' ')

        val delta = deltaValue.toInt() * if (operation == "inc") 1 else -1

        val operatorFunc = when (operator) {
            ">"  -> Int::gt
            ">=" -> Int::gte
            "<"  -> Int::lt
            "<=" -> Int::lte
            "==" -> Int::equals
            "!=" -> Int::notEquals
            else -> error("Unknown operator $operator")
        }

        if (operatorFunc(registers[sourceIndex] ?: 0, compareValue.toInt())) {
            updateRegister(destIndex, delta)
        }
    }

    return registers
}

fun day08a(input: List<String>): Int {
    return applyInstructions(input)
        .values
        .max()!!
}

fun day08b(input: List<String>): Int {
    var maxEver = Int.MIN_VALUE

    applyInstructions(input) { maxEver = max(maxEver, it) }

    return maxEver
}
