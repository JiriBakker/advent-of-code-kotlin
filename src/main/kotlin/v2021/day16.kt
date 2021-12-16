package v2021

import util.productOf
import v2021.Packet.Companion.bitsToPacket

private fun String.parseBits() =
    toCharArray().joinToString("") { it.toString().toInt(16).toString(2).padStart(4, '0') }

private fun String.getInt(startBit: Int, endBit: Int) =
    substring(startBit, endBit).toInt(2)

private fun String.getValueBits() =
    sequence {
        drop(6).chunked(5).forEach {
            yield(it.drop(1))
            if (it[0] == '0') return@sequence
        }
    }.joinToString("")

private data class Packet(
    val version: Int,
    val typeId: Int,
    val length: Int,
    val value: Long? = null,
    val subPackets: List<Packet> = emptyList()
) {

    val versionSum: Long get() = version + subPackets.sumOf { it.versionSum }

    companion object {
        fun String.bitsToPacket() =
            parse(this)

        fun parse(bits: String): Packet {
            val version = bits.getInt(0, 3)
            val typeId  = bits.getInt(3, 6)

            if (typeId == 4) {
                val valueBits = bits.getValueBits()

                val length = 6 + valueBits.length + (valueBits.length / 4)

                return Packet(version, typeId, length, value = valueBits.toLong(2))
            }

            when (val lengthTypeId = bits.getInt(6, 7)) {
                0 -> {
                    val subPacketsLength = bits.getInt(7, 22)

                    var curIndex = 22
                    val subPackets =
                        buildList {
                            while (curIndex < 22 + subPacketsLength) {
                                val packet = parse(bits.drop(curIndex))
                                add(packet)
                                curIndex += packet.length
                            }
                        }

                    val totalLength = 22 + subPacketsLength

                    return Packet(version, typeId, totalLength, subPackets = subPackets)
                }
                1 -> {
                    val nrOfSubPackets = bits.getInt(7, 18)

                    var curIndex = 18
                    val subPackets =
                        buildList {
                            repeat(nrOfSubPackets) {
                                val packet = parse(bits.drop(curIndex))
                                add(packet)
                                curIndex += packet.length
                            }
                        }

                    val totalLength = 18 + subPackets.sumOf { it.length }

                    return Packet(version, typeId, totalLength, subPackets = subPackets)
                }
                else -> throw Error("Unknown lenght type ID: $lengthTypeId")
            }
        }
    }
}

fun day16a(input: List<String>) =
    input
        .first()
        .parseBits()
        .bitsToPacket()
        .versionSum

private fun Packet.computeValue(): Long {
    return when (typeId) {
        0 -> subPackets.sumOf     { it.computeValue() }
        1 -> subPackets.productOf { it.computeValue() }
        2 -> subPackets.minOf     { it.computeValue() }
        3 -> subPackets.maxOf     { it.computeValue() }
        4 -> value!!
        5 ->
            if (subPackets[0].computeValue() > subPackets[1].computeValue()) 1
            else 0
        6 ->
            if (subPackets[0].computeValue() < subPackets[1].computeValue()) 1
            else 0
        7 ->
            if (subPackets[0].computeValue() == subPackets[1].computeValue()) 1
            else 0
        else -> throw Error("Unknown type ID: $typeId")
    }
}

fun day16b(input: List<String>) =
    input
        .first()
        .parseBits()
        .bitsToPacket()
        .computeValue()
