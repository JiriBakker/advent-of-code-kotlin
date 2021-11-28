package v2016.days.day10

import util.multiplyBy

private interface ValueReceiver

private interface ValueSource {
    fun provideValue(receiver: ValueReceiver): Int
}

private class Bot(val id: Int, var receiverLower: ValueReceiver? = null, var receiverHigher: ValueReceiver? = null): ValueSource, ValueReceiver {
    private val sources = mutableListOf<ValueSource>()
    private var values = listOf<Int>()

    override fun provideValue(receiver: ValueReceiver): Int {
        if (values.isEmpty()) {
            values = sources.map { it.provideValue(this) }
        }

        return if (receiver == receiverLower) {
            values.minOrNull()!!
        } else {
            values.maxOrNull()!!
        }
    }

    fun addSource(source: ValueSource) {
        sources.add(source)
    }

    fun getValues(): List<Int> = values
}

private class InputBin(private val value: Int): ValueSource {
    override fun provideValue(receiver: ValueReceiver): Int {
        return value
    }
}

private class OutputBin(val id: Int, val source: ValueSource): ValueReceiver {
    var value: Int? = null

    fun receive() {
        value = source.provideValue(this)
    }
}

private fun runFactory(input: List<String>): Pair<List<Bot>, List<OutputBin>> {
    val bots = mutableMapOf<Int, Bot>()
    fun getBot(id: Int): Bot = bots.getOrPut(id, { Bot(id) })

    val outputBins = mutableListOf<OutputBin>()

    input.forEach { instruction ->
        val segments = instruction.split(' ')
        when {
            segments[0] == "value" -> {
                val bot = getBot(segments[5].toInt())
                bot.addSource(InputBin(segments[1].toInt()))
            }
            else -> {
                val bot = getBot(segments[1].toInt())

                if (segments[5] == "bot") {
                    val receiverLower = getBot(segments[6].toInt())
                    bot.receiverLower = receiverLower
                    receiverLower.addSource(bot)
                } else {
                    val outputBin = OutputBin(segments[6].toInt(), bot)
                    outputBins.add(outputBin)
                    bot.receiverLower = outputBin
                }

                if (segments[10] == "bot") {
                    val receiverHigher = getBot(segments[11].toInt())
                    bot.receiverHigher = receiverHigher
                    receiverHigher.addSource(bot)
                } else {
                    val outputBin = OutputBin(segments[11].toInt(), bot)
                    outputBins.add(outputBin)
                    bot.receiverHigher = outputBin
                }
            }
        }
    }

    outputBins.forEach { it.receive() }

    return bots.values.toList() to outputBins
}

fun day10a(input: List<String>, targetValue1: Int = 61, targetValue2: Int = 17): Int {
    val (bots, _) = runFactory(input)

    fun Bot.hasTargetValues(): Boolean = this.getValues().containsAll(listOf(targetValue1, targetValue2))

    return bots.first(Bot::hasTargetValues).id
}

fun day10b(input: List<String>): Int {
    val (_, outputBins) = runFactory(input)

    return outputBins
        .filter { it.id <= 2 }
        .multiplyBy { it.value!! }
}