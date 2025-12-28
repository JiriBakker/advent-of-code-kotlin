import util.leastCommonMultiple

fun day20a(input: List<String>): Long {
    return pressButton(input, 1000)
}

fun day20b(input: List<String>): Long {
    return pressButton(input, Int.MAX_VALUE)
}
private fun pressButton(input: List<String>, nrOfButtonPresses: Int): Long {
    val modules = mutableMapOf<String, CommunicationModule>()

    input
        .map { line ->
            val module = line.parseModule()
            val destinations = line.split(" -> ")[1].split(", ")

            modules[module.key] = module

            module to destinations
        }
        .forEach { (module, destinations) ->
            destinations.forEach { destination ->
                if (destination !in modules) {
                    modules[destination] = TestingModule(destination)
                }
                module.outputModules.add(modules[destination]!!)
            }
        }

    modules
        .values
        .filter { it is ConjunctionModule }
        .forEach { conjunctionModule ->
            modules
                .filter { module -> module.value.outputModules.any { it == conjunctionModule } }
                .forEach { (key, _) ->
                    (conjunctionModule as ConjunctionModule).lastPulseTypesPerInput[key] = "low"
                }
        }

    val inputsAffectingRx =
        if ("rx" in modules) {
            val rxInput = modules.values.first { module -> module.outputModules.any { it.key == "rx" } }
            modules.values.filter { module -> module.outputModules.any { it == rxInput } }.toSet()
        } else {
            emptySet()
        }

    val todo = mutableListOf<PulseEvent>()

    var lowPulseCount = 0L
    var highPulseCount = 0L

    val specialCycles = mutableMapOf<String, MutableList<Long>>()
    var buttonPresses = 1L

    repeat(nrOfButtonPresses) {
        todo.add(PulseEvent("low", "button", "broadcaster"))
        buttonPresses++

        while (todo.isNotEmpty()) {
            val (pulseType, sourceKey, targetKey) = todo.removeFirst()

            if (pulseType == "low") {
                lowPulseCount++
            } else {
                highPulseCount++
            }

            when (val module = modules[targetKey]!!) {
                is Broadcaster ->
                    module.outputModules.forEach { target ->
                        todo.add(PulseEvent(pulseType, module.key, target.key))
                    }

                is FlipFlopModule -> {
                    if (pulseType == "low") {
                        val outputPulseType = if (module.isOn) "low" else "high"
                        module.outputModules.forEach { target ->
                            todo.add(PulseEvent(outputPulseType, module.key, target.key))
                        }
                        module.isOn = !module.isOn
                    }
                }

                is ConjunctionModule -> {
                    module.lastPulseTypesPerInput[sourceKey] = pulseType

                    val outputPulseType = if (module.lastPulseTypesPerInput.all { it.value == "high" }) "low" else "high"
                    module.outputModules.forEach { target ->
                        todo.add(PulseEvent(outputPulseType, module.key, target.key))
                    }

                    if (outputPulseType == "high" && module in inputsAffectingRx) {
                        if (module.key !in specialCycles) {
                            specialCycles[module.key] = mutableListOf(buttonPresses)
                        } else if (specialCycles[module.key]!!.size == 1) {
                            specialCycles[module.key] = mutableListOf(specialCycles[module.key]!!.first(), buttonPresses)
                        }

                        if (specialCycles.size == 4 && specialCycles.all { it.value.size == 2 }) {
                            val cycleSizes = specialCycles.values.map { values ->
                                values[1] - values[0]
                            }
                            return leastCommonMultiple(cycleSizes)
                        }
                    }
                }
            }
        }
    }

    return lowPulseCount * highPulseCount
}

private abstract class CommunicationModule(
    val key: String,
    val outputModules: MutableList<CommunicationModule> = mutableListOf(),
)
private class Broadcaster() : CommunicationModule("broadcaster")

private class FlipFlopModule(key: String) : CommunicationModule(key) {
    var isOn: Boolean = false
}

private class ConjunctionModule(key: String) : CommunicationModule(key) {
    val lastPulseTypesPerInput = mutableMapOf<String, String>()
}

private class TestingModule(key: String) : CommunicationModule(key)

private data class PulseEvent(
    val pulseType: String,
    val sourceKey: String,
    val targetKey: String,
)

private fun String.parseModule(): CommunicationModule {
    val key = this.split(" -> ").first()
    if (key == "broadcaster") {
        return Broadcaster()
    }
    if (key.startsWith("%")) {
        return FlipFlopModule(key.drop(1))
    }
    if (key.startsWith("&")) {
        return ConjunctionModule(key.drop(1))
    }

    throw Exception("Unknown communication module key: $key")
}