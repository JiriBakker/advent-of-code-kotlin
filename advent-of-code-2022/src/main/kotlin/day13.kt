import PacketInput.Companion.parsePacket

fun day13a(input: List<String>) =
    input
        .chunked(3)
        .map { (line1, line2, _) ->
            val packet1 = parsePacket(line1)
            val packet2 = parsePacket(line2)
            packet1.compareTo(packet2)
        }
        .mapIndexed { index, result ->
            if (result < 0) index + 1
            else 0
        }
        .sum()

fun day13b(input: List<String>): Int {
    val packets =
        input
            .filter { it.isNotEmpty() }
            .plus(listOf("[[2]]", "[[6]]"))
            .map { parsePacket(it) }

    val sortedPackets = packets.sortedWith(PacketInput::compareTo)

    val indexOf2Divider = sortedPackets.indexOfFirst { it.toString() == "[[2]]" } + 1
    val indexOf6Divider = sortedPackets.indexOfFirst { it.toString() == "[[6]]" } + 1

    return indexOf2Divider * indexOf6Divider
}

sealed class PacketInput {

    abstract fun compareTo(other: PacketInput): Int

    override fun toString(): String {
        return when (this) {
            is SingleInteger -> this.nr.toString()
            is PacketInputList -> "[${this.children.joinToString(",")}]"
        }
    }

    companion object {
        fun parsePacket(input: String): PacketInput {
            fun parse(input: String): Pair<PacketInput, Int> {
                val children = mutableListOf<PacketInput>()
                var index = 0
                while (index in input.indices) {
                    if (input[index].isDigit()) {
                        val nr = input.drop(index).takeWhile { it.isDigit() }
                        children.add(SingleInteger(nr.toInt()))
                        index += nr.length
                    } else if (input[index] == '[') {
                        val (nestedChildren, lengthParsed) = parse(input.drop(index + 1))
                        children.add(nestedChildren)
                        index += lengthParsed + 2
                    } else if (input[index] == ']') {
                        break
                    } else {
                        index++
                    }
                }
                return PacketInputList(children) to index
            }
            return parse(input.drop(1).dropLast(1)).first
        }
    }
}

class PacketInputList(val children: List<PacketInput>): PacketInput() {
     override operator fun compareTo(other: PacketInput): Int {
        return when (other) {
            is SingleInteger -> this.compareTo(PacketInputList(listOf(other)))
            is PacketInputList ->
                this.children.zip(other.children)
                    .map { it.first.compareTo(it.second) }
                    .firstOrNull { it != 0 }
                        ?: this.children.size.compareTo(other.children.size)
        }
    }
}

class SingleInteger(val nr: Int) : PacketInput() {
    override operator fun compareTo(other: PacketInput): Int {
        return when (other) {
            is SingleInteger -> this.nr.compareTo(other.nr)
            is PacketInputList -> PacketInputList(listOf(this)).compareTo(other)
        }
    }
}
