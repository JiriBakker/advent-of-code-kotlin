import java.util.ArrayDeque

private fun runNetwork(intCodes: Map<Long, Long>): Sequence<Long> {
    var natPacket: Pair<Long, Long>? = null

    val inputQueues = (0..49).map { ArrayDeque<Long>() }
    val outputQueues = (0..49).map { ArrayDeque<Long>() }

    var loop = 0L
    var lastLoopWithIdleNetwork = 0L

    fun isNetworkIdle(): Boolean = inputQueues.none { it.isNotEmpty() } && outputQueues.none { it.isNotEmpty() }

    val computers = (0..49).map { address ->
        inputQueues[address].add(address.toLong())
        address to stepProgram(intCodes.toMutableMap()) { inputQueues[address].pollFirst() ?: -1 }.iterator()
    }.toMap()

    return sequence {
        while (true) {
            computers.forEach { (address, computer) ->
                val output = computer.next()
                if (output != null) {
                    outputQueues[address].add(output)
                }
                if (outputQueues[address].size == 3) {
                    val recipientAddress = outputQueues[address].poll().toInt()
                    val x = outputQueues[address].poll()
                    val y = outputQueues[address].poll()

                    if (recipientAddress == 255) {
                        natPacket = x to y
                    } else {
                        inputQueues[recipientAddress].add(x)
                        inputQueues[recipientAddress].add(y)
                    }
                }
            }
            val currentNatPacket = natPacket
            if (loop - lastLoopWithIdleNetwork > 100 && currentNatPacket != null && isNetworkIdle()) {
                yield(currentNatPacket.second)

                lastLoopWithIdleNetwork = loop

                inputQueues[0].add(currentNatPacket.first)
                inputQueues[0].add(currentNatPacket.second)
            }
            loop++
        }
    }
}

fun day23a(input: String): Long {
    val intCodes = parseIntCodes(input)
    return runNetwork(intCodes).first()
}

fun day23b(input: String): Long {
    val intCodes = parseIntCodes(input)

    val deliveredNatYValues = mutableSetOf<Long>()

    runNetwork(intCodes)
        .forEach { y ->
            if (deliveredNatYValues.contains(y)) {
                return y
            }
            deliveredNatYValues.add(y)
        }

    error("No NAT packet Y value delivered twice")
}