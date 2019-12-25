package v2015.days.day25

fun day25a(input: String): Long {
    val (targetRow, targetColumn) = input.split(" ").drop(16).map { it.trimEnd(',').trimEnd('.') }.filter { it != "column" }.map { it.toLong() }

    fun getOffsetInSection(column: Long) = column - 1
    fun getSectionBaseOffset(row: Long, column: Long) = (1L until (row + column - 1)).sum() + 1

    val targetOffset = getSectionBaseOffset(targetRow, targetColumn) + getOffsetInSection(targetColumn)

    return (1 until targetOffset)
        .fold(20151125L) { value, _ ->
            (value * 252533) % 33554393
        }
}