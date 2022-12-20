import Day19.Robot.CLAY
import Day19.Robot.GEODE
import Day19.Robot.OBSIDIAN
import Day19.Robot.ORE
import Day19.determineMaxGeodes
import Day19.parseBlueprints
import util.max
import util.productOf
import java.util.PriorityQueue

fun day19a(input: List<String>) =
    input
        .parseBlueprints()
        .sumOf { blueprint ->
            val maxGeodes = blueprint.determineMaxGeodes(maxMinutes = 24)
            blueprint.id * maxGeodes
        }

fun day19b(input: List<String>) =
    input
        .parseBlueprints()
        .take(3)
        .productOf { blueprint ->
            blueprint.determineMaxGeodes(maxMinutes = 32)
        }

object Day19 {

    fun Blueprint.determineMaxGeodes(maxMinutes: Int): Int {
        var maxGeodes = 0

        val toCheck = PriorityQueue<State> { a, b -> b.geode.compareTo(a.geode) * 2 + b.minutesRemaining.compareTo(a.minutesRemaining) }
        toCheck.add(State(listOf(ORE), 0, 0, 0, 0, maxMinutes, this, null))

        while (toCheck.isNotEmpty()) {
            val state = toCheck.poll()

            if (state.minutesRemaining == 0) {
                maxGeodes = max(maxGeodes, state.geode)
                continue
            }

            if (state.canAffordGeodeRobot()) {
                toCheck.add(state.produceGeodeRobot())
                continue
            }

            toCheck.add(state.progressMinute().copy(prev = state))

            if (state.canAffordObsidianRobot() && state.couldUseMoreObsidian() && !state.choseNotToProduceObsidianRobot()) {
                toCheck.add(state.produceObsidanRobot())
            }

            if (state.canAffordClayRobot() && state.couldUseMoreClay() && !state.choseNotToProduceClayRobot()) {
                toCheck.add(state.produceClayRobot())
            }

            if (state.canAffordOreRobot() && state.couldUseMoreOre() && !state.choseNotToProduceOreRobot()) {
                toCheck.add(state.produceOreRobot())
            }
        }

        return maxGeodes
    }

    data class State(
        val robots: List<Robot>,
        val ore: Int,
        val clay: Int,
        val obsidian: Int,
        val geode: Int,
        val minutesRemaining: Int,
        val blueprint: Blueprint,
        val prev: State?
    ) {
        fun produceOreRobot() =
            copy(
                ore = ore - blueprint.oreOreCost,
                prev = this
            )
                .progressMinute()
                .copy(robots = robots.plus(ORE))

        fun produceClayRobot() =
            copy(
                ore = ore - blueprint.clayOreCost,
                prev = this
            )
                .progressMinute()
                .copy(robots = robots.plus(CLAY))

        fun produceObsidanRobot() =
            copy(
                ore = ore - blueprint.obsidianOreCost,
                clay = clay - blueprint.obsidianClayCost,
                prev = this
            )
                .progressMinute()
                .copy(robots = robots.plus(OBSIDIAN))

        fun produceGeodeRobot() =
            copy(
                ore = ore - blueprint.geodeOreCost,
                obsidian = obsidian - blueprint.geodeObsidianCost,
                prev = this
            )
                .progressMinute()
                .copy(robots = robots.plus(GEODE))

        fun progressMinute() =
            copy(
                ore = ore + robots.count { it == ORE },
                clay = clay + robots.count { it == CLAY },
                obsidian = obsidian + robots.count { it == OBSIDIAN },
                geode = geode + robots.count { it == GEODE },
                minutesRemaining = minutesRemaining - 1
            )

        fun canAffordOreRobot() = blueprint.oreOreCost <= ore
        fun canAffordClayRobot() = blueprint.clayOreCost <= ore
        fun canAffordObsidianRobot() = blueprint.obsidianOreCost <= ore && blueprint.obsidianClayCost <= clay
        fun canAffordGeodeRobot() = blueprint.geodeOreCost <= ore && blueprint.geodeObsidianCost <= obsidian

        fun couldUseMoreObsidian() = obsidian + obsidianRobotCount * minutesRemaining < (minutesRemaining * maxObsidianSpendPerMinute)
        fun couldUseMoreClay() = clay + clayRobotCount * minutesRemaining < (minutesRemaining * maxClaySpendPerMinute)
        fun couldUseMoreOre() = ore + oreRobotCount * minutesRemaining < (minutesRemaining * maxOreSpendPerMinute)

        fun choseNotToProduceOreRobot() = prev != null && prev.canAffordOreRobot() && prev.robots.size == this.robots.size
        fun choseNotToProduceClayRobot() = prev != null && prev.canAffordClayRobot() && prev.robots.size == this.robots.size
        fun choseNotToProduceObsidianRobot() = prev != null && prev.canAffordObsidianRobot() && prev.robots.size == this.robots.size

        private val maxOreSpendPerMinute = max(blueprint.clayOreCost, blueprint.obsidianOreCost, blueprint.geodeOreCost)
        private val maxClaySpendPerMinute = blueprint.obsidianClayCost
        private val maxObsidianSpendPerMinute = blueprint.geodeObsidianCost

        private val oreRobotCount get() = robots.count { it == ORE }
        private val clayRobotCount get() = robots.count { it == CLAY }
        private val obsidianRobotCount get() = robots.count { it == OBSIDIAN }
    }

    fun List<String>.parseBlueprints() =
        this.map { line ->
            val parts = line.split(" ")
            val id = parts[1].trimEnd(':').toInt()
            val oreRobotCost = parts[6].toInt()
            val clayRobotCost = parts[12].toInt()
            val obsidianOreCost = parts[18].toInt()
            val obsidianClayCost = parts[21].toInt()
            val geodeOreCost = parts[27].toInt()
            val geodeObsidianCost = parts[30].toInt()
            Blueprint(id, oreRobotCost, clayRobotCost, obsidianOreCost, obsidianClayCost, geodeOreCost, geodeObsidianCost)
        }


    enum class Robot {
        ORE,
        CLAY,
        OBSIDIAN,
        GEODE
    }

    data class Blueprint(
        val id: Int,
        val oreOreCost: Int,
        val clayOreCost: Int,
        val obsidianOreCost: Int,
        val obsidianClayCost: Int,
        val geodeOreCost: Int,
        val geodeObsidianCost: Int
    )
}