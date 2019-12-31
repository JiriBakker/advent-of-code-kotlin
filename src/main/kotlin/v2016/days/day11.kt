package v2016.days.day11

import util.combine
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
            .map { item -> item.split(" ").drop(1).joinToString("") { it.take(2).toUpperCase() }.dropLast(1) }
            .toMutableList()
    }
}

private class State(val floors: List<List<String>>, val elevatorFloor: Int, val steps: Int, var prevState: State? = null) {
    val hash = floors.joinToString("_") { floor -> floor.sorted().joinToString("-") } + "_E$elevatorFloor"

    fun isTargetState(): Boolean = (0..2).all { floors[it].isEmpty() }
    fun isValidState(): Boolean = floors.none { floor ->
        val generators = floor.filter { it.endsWith("G") }
        val chips = floor.filter { it.endsWith("M") }

        val chipsWithoutGenerator = chips.filter { chip -> generators.none { it.dropLast(1) == chip.dropLast(1) } }

        generators.isNotEmpty() && chipsWithoutGenerator.isNotEmpty()
    }

     fun print() {
        floors.reversed().forEachIndexed { nr, objects ->
            print("F${4 - nr} ")
            if (elevatorFloor == 3 - nr) {
                print("E ")
            } else print("  ")
            objects.forEach { print(it.padEnd(4)) }
            println()
        }
        println()
    }
}

private fun findMoves(floors: List<List<String>>): Int {
    val visited = mutableSetOf<String>()
    val toVisit = PriorityQueue<State> { a, b -> a.steps.compareTo(b.steps) }
    toVisit.add(State(floors, 0, 0))

    while (toVisit.isNotEmpty()) {
        val state = toVisit.poll()

        if (state.isTargetState()) {
            sequence {
                var curState = state
                do {
                    yield(curState)

                    curState = curState.prevState
                } while (curState != null)
            }.toList().reversed().forEach(State::print)
            return state.steps
        }

        if (!visited.add(state.hash)) {
            continue
        }

        val itemsOnElevatorFloor = state.floors[state.elevatorFloor]
        val itemCombinationsToTake = itemsOnElevatorFloor.combine(2).filter { it[0] != it[1] }.map { it.sorted() }.distinct()

        itemsOnElevatorFloor.forEach { item ->
            val nextItemsOnElevatorFloor = state.floors[state.elevatorFloor].minus(item)
            if (state.elevatorFloor > 0) {
                val itemsOnDestinationFloor = state.floors[state.elevatorFloor - 1].plus(item)
                val nextFloors = state.floors.mapIndexed { index, floor ->
                    when (index) {
                        state.elevatorFloor -> nextItemsOnElevatorFloor
                        state.elevatorFloor - 1 -> itemsOnDestinationFloor
                        else -> floor
                    }
                }
                val nextState = State(nextFloors, state.elevatorFloor - 1, state.steps + 1, state)
                if (nextState.isValidState() && !visited.contains(nextState.hash)) {
                    toVisit.add(nextState)
                }
            }
        }

        itemCombinationsToTake.forEach { itemCombination ->
            val nextItemsOnElevatorFloor = state.floors[state.elevatorFloor].minus(itemCombination)
            if (state.elevatorFloor < 3) {
                val itemsOnDestinationFloor = state.floors[state.elevatorFloor + 1].plus(itemCombination)
                val nextFloors = state.floors.mapIndexed { index, floor ->
                    when (index) {
                        state.elevatorFloor -> nextItemsOnElevatorFloor
                        state.elevatorFloor + 1 -> itemsOnDestinationFloor
                        else -> floor
                    }
                }
                val nextState = State(nextFloors, state.elevatorFloor + 1, state.steps + 1, state)
                if (nextState.isValidState() && !visited.contains(nextState.hash)) {
                    toVisit.add(nextState)
                }
            }
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