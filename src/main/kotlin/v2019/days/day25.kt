package v2019.days.day25

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes
import java.util.ArrayDeque

private const val NEWLINE = 10L

fun day25a(input: String): Long {
    val intCodes = parseIntCodes(input)

    while (true) {
        val inputQueue = ArrayDeque<Long>()
        generateProgramOutput(intCodes) {
            if (inputQueue.isEmpty()) {
                readLine()?.forEach { inputQueue.add(it.code.toLong()) }
                inputQueue.add(NEWLINE)
            }
            inputQueue.poll()
        }.forEach {
            print(it.toInt().toChar())
        }
    }
}

