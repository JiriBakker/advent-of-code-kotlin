package v2020

import java.math.BigInteger

private fun String.extractMask(): String =
    this.substring(7)

private fun String.extractMemIndex(): Long =
    this.split("]").first().split("[").last().toLong()

private fun String.extractDecimal(): Long =
    this.split("= ").last().toLong()

private fun BigInteger.filter(inputMask: BigInteger, filterMask: BigInteger): Long =
    this.and(inputMask).or(filterMask).toLong()

fun day14a(input: List<String>): Long {
    val mem = mutableMapOf<Long, Long>()

    var inputMask = BigInteger.ZERO
    var filterMask = BigInteger.ZERO

    input.forEach { line ->
        if (line.startsWith("mask")) {
            inputMask = BigInteger.ZERO
            filterMask = BigInteger.ZERO
            line.extractMask().forEachIndexed { index, char ->
                when (char) {
                    '0' -> filterMask = filterMask.clearBit(35 - index)
                    '1' -> filterMask = filterMask.setBit(35 - index)
                    'X' -> inputMask = inputMask.setBit(35 - index)
                }
            }
        } else {
            val bigInt = line.extractDecimal().toBigInteger()
            mem[line.extractMemIndex()] = bigInt.filter(inputMask, filterMask)
        }
    }

    return mem.values.sum()
}

fun day14b(input: List<String>): Long {
    val mem = mutableMapOf<Long, Long>()

    var inputMask = BigInteger.ZERO
    var floatingMasks = mutableListOf<BigInteger>()

    input.forEach { line ->
        if (line.startsWith("mask")) {
            var baseMask = BigInteger.ZERO
            inputMask = BigInteger.ZERO

            line.extractMask().forEachIndexed { index, char ->
                when (char) {
                    '0' -> inputMask = inputMask.setBit(35 - index)
                    '1' -> baseMask = baseMask.setBit(35 - index)
                }
            }

            floatingMasks = mutableListOf(baseMask)
            line.extractMask().forEachIndexed { index, char ->
                if (char == 'X') {
                    floatingMasks.addAll(floatingMasks.map { mask -> mask.flipBit(35 - index) })
                }
            }
        } else {
            val bigInt = line.extractMemIndex().toBigInteger()
            val decimal = line.extractDecimal()

            floatingMasks.forEach { floatingMask ->
                val memIndex = bigInt.filter(inputMask, floatingMask)
                mem[memIndex] = decimal
            }
        }
    }

    return mem.values.sum()
}
