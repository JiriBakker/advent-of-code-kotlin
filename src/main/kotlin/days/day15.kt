package days.day15

import java.util.BitSet
import java.util.LinkedList
import java.util.PriorityQueue

private interface IPositioned {
    val x: Int
    val y: Int
}

private enum class FighterType {
    ELF,
    GOBLIN
}

private const val STARTING_HIT_POINTS = 200

private class Fighter private constructor(val type: FighterType, startX: Int, startY: Int, val attackPower: Int) : IPositioned {
    override var x = startX
        private set

    override var y = startY
        private set

    var hitPoints = STARTING_HIT_POINTS
        private set

    companion object {
        fun elf(startX: Int, startY: Int, attackPower: Int): Fighter {
            return Fighter(FighterType.ELF, startX, startY, attackPower)
        }
        fun goblin(startX: Int, startY: Int): Fighter {
            return Fighter(FighterType.GOBLIN, startX, startY, 3)
        }
    }

    fun receiveAttack(damage: Int) {
        hitPoints -= damage
    }

    fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}

private class Action(val target: Fighter, val stepsNeeded: Int, val firstStepX: Int, val firstStepY: Int)

private class Cell private constructor (override val x: Int, override val y: Int, val isWall: Boolean, occupiedBy: Fighter?) : IPositioned {
    var occupiedBy = occupiedBy
        private set

    companion object {
        fun wall(x: Int, y: Int): Cell {
            return Cell(x, y, true, null)
        }

        fun empty(x: Int, y: Int): Cell {
            return Cell(x, y, false, null)
        }

        fun withFighter(x: Int, y: Int, fighter: Fighter): Cell {
            return Cell(x, y, false, fighter)
        }
    }

    fun claim(fighter: Fighter) {
        occupiedBy = fighter
    }

    fun unclaim() {
        occupiedBy = null
    }

    fun distanceTo(x: Int, y: Int): Int {
        return Math.abs(this.x - x) + Math.abs(this.y - y)
    }
}

private class Battlefield private constructor(private val fighters: MutableList<Fighter>, private val grid: Array<Array<Cell>>) {
    var roundsCompleted = 0
        private set

    val height = grid.size
    val width = grid[0].size

    var nrOfElfsRemaining = fighters.count { it.type == FighterType.ELF }
        private set

    var nrOfGoblinsRemaining = fighters.count { it.type == FighterType.GOBLIN }
        private set

    fun sumHitPoints(): Int {
        return fighters.sumBy { it.hitPoints }
    }

    fun printBattlefield() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(when {
                    grid[y][x].isWall -> '#'
                    grid[y][x].occupiedBy?.type == FighterType.ELF -> 'E'
                    grid[y][x].occupiedBy?.type == FighterType.GOBLIN -> 'G'
                    else -> '.'
                })
            }
            print(" ")
            grid[y]
                .filter { it.occupiedBy != null }
                .map { it.occupiedBy!! }
                .map { print("${if (it.type == FighterType.ELF) 'E' else 'G'}(${it.hitPoints}), ") }
            println()
        }
    }

    companion object {
        fun parse(inputLines: List<String>, elfAttackPower: Int): Battlefield {
            val height = inputLines.size
            val width = inputLines[0].length
            val fighters = mutableListOf<Fighter>()
            val grid: MutableList<MutableList<Cell>> = mutableListOf()
            for (y in 0 until height) {
                grid.add(mutableListOf())
                for (x in 0 until width) {
                    val cell = when (inputLines[y][x]) {
                        'E' -> {
                            val elf = Fighter.elf(x, y, elfAttackPower)
                            fighters.add(elf)
                            Cell.withFighter(x, y, elf)
                        }
                        'G' -> {
                            val goblin = Fighter.goblin(x, y)
                            fighters.add(goblin)
                            Cell.withFighter(x, y, goblin)
                        }
                        '.' -> Cell.empty(x, y)
                        else -> Cell.wall(x, y)
                    }
                    grid[y].add(cell)
                }
            }
            return Battlefield(fighters, grid.map { it.toTypedArray() }.toTypedArray())
        }
    }

    private fun <T : IPositioned> List<T>.sortedByReadingOrder(): List<T> {
        return this.sortedWith(Comparator<IPositioned> { a, b -> (a.y * width + a.x).compareTo(b.y * width + b.x) })
    }

    private fun findPath(fighter: Fighter, otherFighter: Fighter): Triple<Int, Int, Int>? {
        val seen = BitSet()
        val toVisit = PriorityQueue<Triple<Int, Cell, Int>> { p0, p1 -> p0.first.compareTo(p1.first) }

        fun offerToVisit(stepsNeeded: Int, x: Int, y: Int) {
            val priority = stepsNeeded * width * height + y * width + x
            toVisit.offer(Triple(priority, grid[y][x], stepsNeeded))
        }

        fun addDirectNeighbours(x: Int, y: Int, stepsNeeded: Int) {
            fun addIfNotSeenYet(x: Int, y: Int) {
                var cell = grid[y][x]
                if (cell.isWall || (cell.occupiedBy != null && cell.occupiedBy != otherFighter)) {
                    return
                }
                val seenBit = y * width + x
                if (!seen.get(seenBit)) {
                    offerToVisit(stepsNeeded, x, y)
                    seen.set(seenBit)
                }
            }

            addIfNotSeenYet(x, y + 1)
            addIfNotSeenYet(x + 1, y)
            addIfNotSeenYet(x - 1, y)
            addIfNotSeenYet(x, y - 1)
        }

        offerToVisit(0, otherFighter.x, otherFighter.y)
        addDirectNeighbours(otherFighter.x, otherFighter.y, 1)

        while (toVisit.isNotEmpty()) {
            val (_, currentCell, stepsNeeded) = toVisit.poll()
            if (currentCell.distanceTo(fighter.x, fighter.y) == 1) {
                return Triple(stepsNeeded, currentCell.x, currentCell.y)
            }

            addDirectNeighbours(currentCell.x, currentCell.y, stepsNeeded + 1)
        }

        return null
    }

    private fun determineAction(fighter: Fighter): Action? {
        val orderedFighters = fighters
            .filter { it != fighter && it.type != fighter.type }
            .sortedByReadingOrder()

        var currentOptimalAction: Action? = null
        for (otherFighter in orderedFighters) {
            val path = findPath(fighter, otherFighter) ?: continue
            val (stepsNeeded, firstStepX, firstStepY) = path

            val isCloser = currentOptimalAction != null && stepsNeeded < currentOptimalAction.stepsNeeded
            val isWithinRangeAndHasLessHitpoints =
                currentOptimalAction != null &&
                    stepsNeeded <= 1 &&
                    currentOptimalAction.stepsNeeded == stepsNeeded &&
                    otherFighter.hitPoints < currentOptimalAction.target.hitPoints

            if (currentOptimalAction == null || isCloser || isWithinRangeAndHasLessHitpoints) {
                currentOptimalAction = Action(otherFighter, stepsNeeded, firstStepX, firstStepY)
            }
        }
        return currentOptimalAction
    }

    private fun move(fighter: Fighter, x: Int, y: Int) {
        grid[fighter.y][fighter.x].unclaim()
        grid[y][x].claim(fighter)
        fighter.moveTo(x, y)
    }

    private fun isBattleFinished(): Boolean {
        return nrOfElfsRemaining == 0 || nrOfGoblinsRemaining == 0
    }

    fun runBattle(haltIfElfDies: Boolean) {
        while (true) {
            val orderedFighters = LinkedList<Fighter>(fighters.sortedByReadingOrder())
            while (orderedFighters.isNotEmpty()) {
                if (isBattleFinished()) {
                    return
                }
                val fighter = orderedFighters.poll()
                val action = determineAction(fighter) ?: continue

                if (action.stepsNeeded > 0) {
                    move(fighter, action.firstStepX, action.firstStepY)
                }
                if (action.stepsNeeded <= 1) {
                    action.target.receiveAttack(fighter.attackPower)
                    if (action.target.hitPoints <= 0) {
                        fighters.remove(action.target)
                        orderedFighters.remove(action.target)
                        grid[action.target.y][action.target.x].unclaim()
                        when (action.target.type) {
                            FighterType.ELF -> nrOfElfsRemaining--
                            FighterType.GOBLIN -> nrOfGoblinsRemaining--
                        }
                        if (haltIfElfDies && action.target.type == FighterType.ELF) {
                            return
                        }
                    }
                }
            }
            roundsCompleted++
        }
    }
}

fun day15a(inputLines: List<String>): Int {
    val battlefield = Battlefield.parse(inputLines, 3)
    battlefield.runBattle(false)
    return battlefield.roundsCompleted * battlefield.sumHitPoints()
}

fun day15b(inputLines: List<String>): Int? {
    fun runBattle(elfAttackPower: Int): Pair<Boolean, Int> {
        val battlefield = Battlefield.parse(inputLines, elfAttackPower)
        battlefield.runBattle(true)
        return Pair(battlefield.nrOfGoblinsRemaining == 0, battlefield.roundsCompleted * battlefield.sumHitPoints())
    }

    var minElfAttackPower = 3
    var maxElfAttackPower = STARTING_HIT_POINTS

    var lastSuccessResult: Int? = null
    while (true) {
        val midElfAttackPower = minElfAttackPower + ((maxElfAttackPower - minElfAttackPower) / 2)

        val midElfAttackPowerResult = runBattle(midElfAttackPower)
        if (midElfAttackPowerResult.first) {
            maxElfAttackPower = midElfAttackPower
            lastSuccessResult = midElfAttackPowerResult.second
        } else {
            minElfAttackPower = midElfAttackPower
        }

        if (minElfAttackPower == maxElfAttackPower - 1) {
            return lastSuccessResult
        }
    }
}
