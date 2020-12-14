package v2020.days.day14

import java.math.BigInteger



fun day14a(input: List<String>): Long {
    var mask0 = BigInteger.ZERO
    var mask1 = BigInteger.ZERO
    val mem = mutableMapOf<Int, BigInteger>()

    input.forEach { line ->
        if (line.startsWith("mask")) {
            val maskString = line.substring(7)
            mask0 = BigInteger.ZERO
            mask1 = BigInteger.ZERO
            maskString.forEachIndexed { index, char ->
                when (char) {
                    '0' -> mask0 = mask0.clearBit(35 - index)
                    '1' -> mask0 = mask0.setBit(35 - index)
                    'X' -> mask1 = mask1.setBit(35 - index)
                }
            }
        } else {
            val memIndex = line.split("]").first().split("[").last().toInt()
            val decimal = line.split("= ").last().toLong()
            val bigInt = BigInteger.valueOf(decimal)
            mem[memIndex] = bigInt.and(mask1).or(mask0)
        }
    }

    return mem.values.fold(BigInteger.ZERO) { acc, bigInt -> acc.plus(bigInt) }.toLong()
}

fun day14b(input: List<String>): Long {
    var mask0 = BigInteger.ZERO
    var floatingMasks = mutableListOf<BigInteger>()
    val mem = mutableMapOf<Long, Long>()

    input.forEach { line ->
        if (line.startsWith("mask")) {
            val maskString = line.substring(7)
            mask0 = BigInteger.ZERO
            var baseMask = BigInteger.ZERO
            floatingMasks = mutableListOf()

            maskString.forEachIndexed { index, char ->
                when (char) {
                    '0' -> mask0 = mask0.setBit(35 - index)
                    '1' -> baseMask = baseMask.setBit(35 - index)
                }
            }

            floatingMasks.add(baseMask)
            maskString.forEachIndexed { index, char ->
                if (char == 'X') {
                    floatingMasks.addAll(floatingMasks.map { mask -> mask.flipBit(35 - index) })
                }
            }
        } else {
            val memIndex = line.split("]").first().split("[").last().toLong()
            val decimal = line.split("= ").last().toLong()
            val bigInt = BigInteger.valueOf(memIndex)

            floatingMasks.forEach { floatingMask ->
                val newMemIndex = bigInt.and(mask0).or(floatingMask)
                mem[newMemIndex.toLong()] = decimal
            }
        }
    }

    return mem.values.fold(0L) { acc, long -> acc.plus(long) }
}

