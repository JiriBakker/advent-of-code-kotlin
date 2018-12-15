package days.day15

import java.util.PriorityQueue

private enum class FighterType {
    ELF,
    GOBLIN
}

private class Fighter private constructor(val type: FighterType, startX: Int, startY: Int, val attackPower: Int) {
    var x = startX
        private set

    var y = startY
        private set

    var hitPoints = 200
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

private class Cell private constructor (val x: Int, val y: Int, val isWall: Boolean, occupiedBy: Fighter?) {
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
        if (isWall) {
            throw Exception("[$x,$y] Not allowed to move into a wall")
        }
        if (occupiedBy != null) {
            throw Exception("[$x,$y] Already occupied by fighter")
        }
        occupiedBy = fighter
    }

    fun unclaim() {
        if (occupiedBy == null) {
            throw Exception("Nobody was occupying this cell")
        }
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
    val nrOfNonWalls = grid.sumBy { row -> row.sumBy { if (it.isWall) 0 else 1 } }

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

    private fun findPath(fighter: Fighter, otherFighter: Fighter): Triple<Int, Int, Int>? {
        val seen = mutableSetOf<Pair<Int, Int>>()
        val toVisit: PriorityQueue<Pair<Cell, Int>> =
            PriorityQueue(nrOfNonWalls) {
                    p0, p1 ->
                        if (p0.second != p1.second) {
                            p0.second - p1.second
                        } else {
                            (p0.first.y * height + p0.first.x).compareTo(p1.first.y * height + p1.first.x)
                        }
                }

        fun addDirectNeighbours(x: Int, y: Int, stepsNeeded: Int) {
            fun addIfNotSeenYet(x: Int, y: Int) {
                if (!seen.contains(Pair(x, y))) {
                    toVisit.offer(Pair(grid[y][x], stepsNeeded))
                    seen.add(Pair(x, y))
                }
            }

            // TODO rearrange for correct order? (Because traversing from target to attacker)
            addIfNotSeenYet(x, y + 1)
            addIfNotSeenYet(x + 1, y)
            addIfNotSeenYet(x - 1, y)
            addIfNotSeenYet(x, y - 1)
        }

        toVisit.offer(Pair(grid[otherFighter.y][otherFighter.x], 0))
        addDirectNeighbours(otherFighter.x, otherFighter.y, 1)

        while (toVisit.isNotEmpty()) {
            val (currentCell, stepsNeeded) = toVisit.poll()
            if (currentCell.isWall || (currentCell.occupiedBy != null && currentCell.occupiedBy != otherFighter)) {
                continue
            }

            if (currentCell.distanceTo(fighter.x, fighter.y) == 1) {
                // println("Found path with $stepsNeeded and firstStep ${currentCell.x},${currentCell.y}")
                return Triple(stepsNeeded, currentCell.x, currentCell.y)
            }

            addDirectNeighbours(currentCell.x, currentCell.y, stepsNeeded + 1)
        }

        return null
    }

    private fun determineAction(fighter: Fighter): Action? {
        val orderedFighters = PriorityQueue<Fighter> { a, b -> (a.y * height + a.x).compareTo(b.y * height + b.x) }
        orderedFighters.addAll(fighters.filter { it != fighter && it.type != fighter.type })

        var currentOptimalAction: Action? = null
        while (orderedFighters.isNotEmpty()) {
            val otherFighter = orderedFighters.poll()
            val path = findPath(fighter, otherFighter) ?: continue
            val (stepsNeeded, firstStepX, firstStepY) = path
            if (currentOptimalAction == null ||
                stepsNeeded < currentOptimalAction.stepsNeeded ||
                (stepsNeeded == 0 && otherFighter.hitPoints < currentOptimalAction.target.hitPoints)) {
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
        while (roundsCompleted < 500) {
//            println("Round $roundsCompleted")
//            printBattlefield()

            val orderedFighters = PriorityQueue<Fighter> { a, b -> (a.y * height + a.x).compareTo(b.y * height + b.x) }
            orderedFighters.addAll(fighters)

            while (orderedFighters.isNotEmpty()) {
                if (isBattleFinished()) {
                    println("Finished after $roundsCompleted with ${sumHitPoints()} HP remaining")
                    printBattlefield()
                    return
                }
                val fighter = orderedFighters.poll()
                val action = determineAction(fighter) ?: continue

                if (action.stepsNeeded > 0) {
                    // println("Moving from [${fighter.x},${fighter.y}] -> [${action.firstStepX},${action.firstStepY}] (stepsNeeded: ${action.stepsNeeded})")
                    move(fighter, action.firstStepX, action.firstStepY)
                }
                if (action.stepsNeeded <= 1) {
                    // println("Attacking from [${fighter.x},${fighter.y}] -> [${action.target.x},${action.target.y}]")

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

    var elfAttackPower = 3
    do {
        val battlefield = Battlefield.parse(inputLines, elfAttackPower)
        battlefield.runBattle(true)
        if (battlefield.nrOfGoblinsRemaining == 0) {
            return battlefield.roundsCompleted * battlefield.sumHitPoints()
        }
        elfAttackPower++
    } while (elfAttackPower <= 50)

    return null
}
