import kotlin.math.abs

fun day02a(input: List<String>) =
    input
        .map { it.split(" ").map(String::toInt) }
        .filter { it.isValidRow() }
        .size

fun day02b(input: List<String>) =
    input
        .map { it.split(" ").map(String::toInt) }
        .filter { row ->
            row.isValidRow()
                || row.indices.any { index -> row.withoutIndex(index).isValidRow() }
        }
        .size

private fun List<Int>.isValidRow(): Boolean {
    for (index in (0 until this.size - 1)) {
        val a = this[index]
        val b = this[index + 1]
        when {
            a == b -> return false            // Cannot be equal
            abs(a - b) > 3 -> return false // Cannot differ more than 3
        }
    }
    for (index in (0 until this.size - 2)) {
        val a = this[index]
        val b = this[index + 1]
        val c = this[index + 2]
        when {
            a > b && b < c -> return false // Cannot decrease, then increase
            a < b && b > c -> return false // Cannot increase, then decrease
        }
    }
    return true
}

private fun List<Int>.withoutIndex(indexToFilter: Int) =
    this.filterIndexed { i, _ -> i != indexToFilter }