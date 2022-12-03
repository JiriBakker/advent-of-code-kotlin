import util.productOf
import Packet.Companion.bitsToPacket

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
    val nrOfBits: Int,
    val value: Long? = null,
    val subPackets: List<Packet> = emptyList()
) {

    val versionSum: Long get() = version + subPackets.sumOf { it.versionSum }

    companion object {
        fun String.bitsToPacket() =
            parse(this)

        fun String.parseSubPackets(isFinished: (List<Packet>) -> Boolean): List<Packet> {
            var curIndex = 0
            val subPackets = mutableListOf<Packet>()
            while (!isFinished(subPackets)) {
                val packet = parse(drop(curIndex))
                subPackets.add(packet)
                curIndex += packet.nrOfBits
            }
            return subPackets
        }

        private fun parse(bits: String): Packet {
            val version = bits.getInt(0, 3)
            val typeId  = bits.getInt(3, 6)

            if (typeId == 4) {
                val valueBits = bits.getValueBits()

                val nrOfBits = 6 + valueBits.length + (valueBits.length / 4)

                return Packet(version, typeId, nrOfBits, value = valueBits.toLong(2))
            }

            val lengthTypeId = bits.getInt(6, 7)
            when (lengthTypeId) {
                0 -> {
                    val nrOfSubPacketBits = bits.getInt(7, 22)

                    val subPackets =
                        bits.drop(22)
                            .parseSubPackets { subPackets ->
                                subPackets.sumOf { it.nrOfBits } == nrOfSubPacketBits
                            }

                    return Packet(
                        version,
                        typeId,
                        nrOfBits = 22 + nrOfSubPacketBits,
                        subPackets = subPackets
                    )
                }
                1 -> {
                    val nrOfSubPackets = bits.getInt(7, 18)

                    val subPackets =
                        bits.drop(18)
                            .parseSubPackets { subPackets ->
                                subPackets.count() == nrOfSubPackets
                            }

                    return Packet(
                        version,
                        typeId,
                        nrOfBits = 18 + subPackets.sumOf { it.nrOfBits },
                        subPackets = subPackets
                    )
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
