fun day06a(input: List<String>): Int {
    val durations = input[0].split(':')[1].split(' ').filter { it.isNotEmpty() }.map(String::toLong)
    val records = input[1].split(':')[1].split(' ').filter { it.isNotEmpty() }.map(String::toLong)

    return durations.zip(records)
        .map { (duration, record) -> countNumberOfWaysToWin(duration, record) }
        .reduce(Int::times)
}

fun day06b(input: List<String>): Int {
    val duration = input[0].split(':')[1].replace(" ", "").toLong()
    val record = input[1].split(':')[1].replace(" ", "").toLong()

    return countNumberOfWaysToWin(duration, record)
}

private fun countNumberOfWaysToWin(duration: Long, record: Long): Int {
    fun beatsRecord(pressTime: Long) =
        pressTime * (duration - pressTime) > record

    val minPressTimeToWin = (1 until duration).first(::beatsRecord)
    val maxPressTimeToWin = (duration - 1 downTo minPressTimeToWin).first(::beatsRecord)

    return (minPressTimeToWin .. maxPressTimeToWin).count()
}
