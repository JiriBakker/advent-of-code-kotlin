import util.permute
import java.util.PriorityQueue

private fun parseInput(input: List<String>): List<MutableList<String>> {
    return input.map { line ->
        line
            .trimEnd('.')
            .split(" ")
            .drop(4)
            .joinToString(" ")
            .split(", and ", ", ", " and ")
            .filter { it != "nothing relevant" }
            .map { item -> item.split(" ").drop(1).joinToString("") { it.take(2).uppercase() }.dropLast(1) }
            .toMutableList()
    }
}

private class State(val floors: List<List<String>>, val elevatorFloor: Int, val steps: Int) {
    val hash = computeHash()

    private fun computeHash(): String {
        val pairLocations = mutableMapOf<String, MutableList<Int>>()
        floors.forEachIndexed { floorNr, objectsOnFloor ->
            objectsOnFloor.forEach { obj ->
                val element = obj.substring(0, 2)
                pairLocations
                    .getOrPut(element) { mutableListOf() }
                    .add(floorNr)
            }
        }

        return pairLocations.values
            .map { it.sorted().joinToString("&") }
            .sorted()
            .joinToString("_") + "_E$elevatorFloor"
    }

    fun isTargetState(): Boolean = (0..2).all { floors[it].isEmpty() }
    fun isValidState(): Boolean = floors.none { floor ->
        val generators = floor.filter { it.endsWith("G") }
        val chips = floor.filter { it.endsWith("M") }

        val chipsWithoutGenerator = chips.filter { chip -> generators.none { it.dropLast(1) == chip.dropLast(1) } }

        generators.isNotEmpty() && chipsWithoutGenerator.isNotEmpty()
    }

    fun move(itemsToMove: List<String>, destinationFloor: Int): State {
        val nextFloors = floors.mapIndexed { floorNr, itemsOnFloor ->
            when (floorNr) {
                elevatorFloor -> itemsOnFloor.minus(itemsToMove)
                destinationFloor -> itemsOnFloor.plus(itemsToMove)
                else -> itemsOnFloor
            }
        }
        return State(nextFloors, destinationFloor, steps + 1)
    }

}

private fun findMoves(floors: List<List<String>>): Int {
    val visited = mutableSetOf<String>()
    val toVisit = PriorityQueue<State> { a, b -> a.steps.compareTo(b.steps) }
    toVisit.add(State(floors, 0, 0))

    while (toVisit.isNotEmpty()) {
        val state = toVisit.poll()

        if (state.isTargetState()) {
            return state.steps
        }

        if (!visited.add(state.hash)) {
            continue
        }

        fun addIfViable(itemCombinations: List<List<String>>, nextFloor: Int) {
            itemCombinations.forEach { itemCombination ->
                val nextState = state.move(itemCombination, nextFloor)
                if (!visited.contains(nextState.hash) && nextState.isValidState()) {
                    toVisit.add(nextState)
                }
            }
        }

        val itemsOnElevatorFloor = state.floors[state.elevatorFloor]
        val itemCombinationsToTake = itemsOnElevatorFloor.permute(2)

        if (state.elevatorFloor > 0) {
            addIfViable(itemsOnElevatorFloor.map { listOf(it) }, state.elevatorFloor - 1)
        }

        if (state.elevatorFloor < 3) {
            addIfViable(itemCombinationsToTake, state.elevatorFloor + 1)
        }
    }

    error("No solution found")
}

fun day11a(input: List<String>): Int {
    val floors = parseInput(input)
    return findMoves(floors)
}

fun day11b(input: List<String>, toAddToFirstFloor: List<String> = listOf("ELG", "ELM", "DIG", "DIM")): Int {
    val floors = parseInput(input)
    floors[0].addAll(toAddToFirstFloor)
    return findMoves(floors)
}