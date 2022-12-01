package v2016

import util.partitionIndexed

private fun parseIpv7(ipv7Address: String): Pair<List<String>, List<String>> {
    val segments = ipv7Address.split('[', ']')
    return segments.partitionIndexed { (index, _) -> index % 2 == 0 }
}

private fun hasTlsSupport(ipv7Address: String): Boolean {
    val (supernets, hypernets) = parseIpv7(ipv7Address)

    fun hasAbba(chars: String): Boolean {
        fun isAbba(index: Int): Boolean {
            return index <= chars.length - 4 &&
                chars[index] == chars[index + 3] &&
                chars[index + 1] == chars[index + 2] &&
                chars[index] != chars[index + 1]
        }
        return (0 .. chars.length - 4).any(::isAbba)
    }

    return supernets.any(::hasAbba) && hypernets.none(::hasAbba)
}

private fun hasSslSupport(ipv7Address: String): Boolean {
    val (supernets, hypernets) = parseIpv7(ipv7Address)

    fun findAbas(chars: String): List<String> {
        fun isAba(index: Int): Boolean {
            return index <= chars.length - 3 &&
                chars[index] == chars[index + 2] &&
                chars[index] != chars[index + 1]
        }
        return (0 .. chars.length - 3).filter(::isAba).map { chars[it].toString() + chars[it + 1] + chars[it + 2] }
    }

    val supernetAbas = supernets.flatMap(::findAbas)
    val hypernetAbas = hypernets.flatMap(::findAbas)

    return supernetAbas.any { aba -> hypernetAbas.any { aba[0] == it[1] && aba[1] == it[0] } }
}

fun day07a(input: List<String>): Int {
    return input.count(::hasTlsSupport)
}

fun day07b(input: List<String>): Int {
    return input.count(::hasSslSupport)
}