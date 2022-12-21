import Day21.parseMonkeys
import Day21.ComputedShout
import Day21.NumberShout

fun day21a(input: List<String>): Long {
    val monkeys = input.parseMonkeys()

    val root = monkeys["root"]!!

    return root.shout().result
}

fun day21b(input: List<String>): Long {
    val monkeys =
        input.parseMonkeys(
            rootOperation = { a, b ->
                if (a is ComputedShout && a.containsHuman()) {
                    a.solveHuman(b.result)
                } else if (b is ComputedShout && b.containsHuman()) {
                    b.solveHuman(a.result)
                } else if (a is NumberShout && a.containsHuman()) {
                    b.result
                } else if (b is NumberShout && b.containsHuman()) {
                    a.result
                } else {
                    throw Exception("Inconsistent state, no human found")
                }
            }
        )

    val root = monkeys["root"]!!

    return root.shout().result
}

object Day21 {

    fun List<String>.parseMonkeys(
        rootOperation: ((MonkeyShout, MonkeyShout) -> Long)? = null
    ): Map<String, Monkey> {
        val monkeys = mutableMapOf<String, Monkey>()
        this.forEach { line ->
            val parts = line.split(" ")
            val name = parts[0].trimEnd(':')
            val monkey =
                if (parts.size == 4) {
                    val operation =
                        if (name == "root" && rootOperation != null) {
                            rootOperation
                        } else {
                            when (parts[2]) {
                                "*" -> { a, b -> a.result * b.result }
                                "/" -> { a, b -> a.result / b.result }
                                "+" -> { a, b -> a.result + b.result }
                                else -> { a, b -> a.result - b.result }
                            }
                        }

                    Monkey(name) {
                        val monkey1 = monkeys[parts[1]]!!.shout()
                        val monkey2 = monkeys[parts[3]]!!.shout()

                        ComputedShout(
                            operation.invoke(monkey1, monkey2),
                            monkey1,
                            monkey2,
                            parts[2]
                        )
                    }
                } else {
                    Monkey(name) { NumberShout(parts[1].toLong(), isHuman = name == "humn") }
                }

            monkeys[name] = monkey
        }
        return monkeys
    }

    class Monkey(val name: String, val shout: () -> MonkeyShout)

    sealed class MonkeyShout {
        abstract val result: Long
        abstract fun containsHuman(): Boolean
    }

    class NumberShout(
        number: Long,
        private val isHuman: Boolean = false
    ) : MonkeyShout() {
        override val result = number
        override fun containsHuman() = isHuman
        override fun toString() = result.toString()
    }

    class ComputedShout(
        override val result: Long,
        private val monkey1: MonkeyShout,
        private val monkey2: MonkeyShout,
        private val operator: String
    ) : MonkeyShout() {
        override fun toString() =
            "($monkey1 $operator $monkey2)"

        override fun containsHuman() =
            monkey1.containsHuman() || monkey2.containsHuman()

        fun solveHuman(otherResult: Long): Long {
            return if (monkey1.containsHuman()) {
                val newResult = when (operator) {
                    "/" -> otherResult * monkey2.result
                    "-" -> otherResult + monkey2.result
                    "+" -> otherResult - monkey2.result
                    "*" -> otherResult / monkey2.result
                    else -> throw Exception("Unknown operator: $operator")
                }
                when (monkey1) {
                    is NumberShout -> newResult
                    is ComputedShout -> monkey1.solveHuman(newResult)
                }
            } else {
                val newResult = when (operator) {
                    "/" -> monkey1.result / otherResult
                    "-" -> monkey1.result - otherResult
                    "+" -> otherResult - monkey1.result
                    "*" -> otherResult / monkey1.result
                    else -> throw Exception("Unknown operator: $operator")
                }

                when (monkey2) {
                    is NumberShout -> newResult
                    is ComputedShout -> monkey2.solveHuman(newResult)
                }
            }
        }
    }
}