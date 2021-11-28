package v2020

private fun findRequired(target: Long): Long {
    var i = 0L
    do {
        i++
        val cur = 5L * i * i + 2 * (i - 1) * i
    } while (cur < target)
    return i
}

fun infiA(): Long {
    return findRequired(17_478_872)
}

fun infiB(): Long {
    return (
        findRequired(4_541_548_390L) +
        findRequired(1_340_917_613) +
        findRequired(747_673_847L) +
        findRequired(430_823_615L) +
        findRequired(368_952_823L) +
        findRequired(42_728_069L)
    ) * 8L
}