package days.day24

import java.util.PriorityQueue

private enum class UnitGroupType {
    Infection,
    ImmuneSystem
}

private class UnitGroup(
    val type: UnitGroupType,
    val groupNr: Int,
    initialNrOfUnits: Int,
    val hitPointsPerUnit: Int,
    val attackDamage: Int,
    val attackType: String,
    val initiative: Int,
    val weaknesses: List<String>,
    val immunities: List<String>
) {
    val id
        get() = "${if (type == UnitGroupType.Infection) "inf" else "imm"}-$groupNr"

    var nrOfUnits = initialNrOfUnits
        private set

    val effectivePower
        get() = nrOfUnits * attackDamage

    fun receiveAttack(attacker: UnitGroup) {
        val damage = computeDamageForAttack(attacker)
        val unitsKilled = damage / hitPointsPerUnit
        nrOfUnits = Math.max(0, nrOfUnits - unitsKilled)
    }

    fun computeDamageForAttack(attacker: UnitGroup): Int {
        return when {
            immunities.contains(attacker.attackType) -> 0
            weaknesses.contains(attacker.attackType) -> 2 * attacker.effectivePower
            else -> attacker.effectivePower
        }
    }

    override fun toString(): String {
        return "[$id, units: $nrOfUnits, hp: $hitPointsPerUnit, dmg: $attackDamage $attackType, power: $effectivePower, init: $initiative${if (weaknesses.isNotEmpty()) ", weak: ${weaknesses.joinToString(",")}" else ""}${if (immunities.isNotEmpty()) ", immune: ${immunities.joinToString(",")}" else ""}]"
    }
}

private fun parse(inputLines: List<String>, immuneSystemBoost: Int = 0): List<UnitGroup> {
    fun parseModifiers(line: String): Pair<List<String>, List<String>> {
        var weaknesses = mutableListOf<String>()
        var immunities = mutableListOf<String>()

        val modifierSections = line.substringAfter('(').substringBeforeLast(')').split("; ")
        for (modifierSection in modifierSections) {
            val parts = modifierSection.split(' ')
            for (i in 2 until parts.size) {
                if (parts[0] == "weak") {
                    weaknesses.add(parts[i].trimEnd(','))
                } else if (parts[0] == "immune") {
                    immunities.add(parts[i].trimEnd(','))
                }
            }
        }
        return Pair(weaknesses, immunities)
    }

    var curUnitGroupType = UnitGroupType.ImmuneSystem
    var groupNr = 1
    return sequence {
        for (line in inputLines) {
            when (line) {
                "" -> Unit
                "Immune System:" -> curUnitGroupType = UnitGroupType.ImmuneSystem
                "Infection:" -> curUnitGroupType = UnitGroupType.Infection
                else -> {
                    val parts = line.split(" ")
                    val indexOfAttackDamage = parts.indexOf("does") + 1

                    val (weaknesses, immunities) = parseModifiers(line)

                    yield(
                        UnitGroup(
                            type = curUnitGroupType,
                            groupNr = groupNr++,
                            initialNrOfUnits = parts[0].toInt(),
                            hitPointsPerUnit = parts[4].toInt(),
                            attackDamage = parts[indexOfAttackDamage].toInt() + (if (curUnitGroupType == UnitGroupType.ImmuneSystem) immuneSystemBoost else 0),
                            attackType = parts[indexOfAttackDamage + 1],
                            initiative = parts.last().toInt(),
                            weaknesses = weaknesses,
                            immunities = immunities
                        )
                    )
                }
            }
        }
    }.toList()
}

private fun selectTargets(unitGroups: List<UnitGroup>): List<Pair<UnitGroup, UnitGroup>> {
    val potentialAttackers = PriorityQueue<UnitGroup>(
        compareByDescending<UnitGroup> { it.effectivePower }
            .thenByDescending { it.initiative }
    )
    unitGroups.forEach { potentialAttackers.offer(it) }

    val potentialDefenders = unitGroups.toMutableList()

    fun findOptimalTarget(attacker: UnitGroup): UnitGroup? {
        return potentialDefenders
            .filter { it.type != attacker.type }
            .maxWith(
                compareBy<UnitGroup> { it.computeDamageForAttack(attacker) }
                .thenBy { it.effectivePower }
                .thenBy { it.initiative }
            )
    }

    val combatPairs = mutableListOf<Pair<UnitGroup, UnitGroup>>()
    while (potentialAttackers.isNotEmpty() && potentialDefenders.isNotEmpty()) {
        val attacker = potentialAttackers.poll()

        val defender = findOptimalTarget(attacker) ?: continue

        if (defender.computeDamageForAttack(attacker) > 0L) {
            potentialDefenders.remove(defender)
            combatPairs.add(Pair(attacker, defender))
        }
    }
    return combatPairs
}

private fun runBattle(unitGroups: MutableList<UnitGroup>) {
    fun isFightOver(): Boolean {
        return unitGroups.none { it.type == UnitGroupType.Infection } || unitGroups.none { it.type == UnitGroupType.ImmuneSystem }
    }

    do {
        val combatPairs = selectTargets(unitGroups)

        var anyKilled = false
        for ((attacker, defender) in combatPairs.sortedByDescending { it.first.initiative }) {
            if (!unitGroups.contains(attacker) || !unitGroups.contains(defender)) {
                continue
            }

            val nrOfUnitsBeforeAttack = defender.nrOfUnits

            defender.receiveAttack(attacker)

            anyKilled = anyKilled || (nrOfUnitsBeforeAttack - defender.nrOfUnits) > 0

            if (defender.nrOfUnits == 0) {
                unitGroups.remove(defender)
            }
        }
    } while (!isFightOver() && anyKilled)
}

fun day24a(inputLines: List<String>): Int {
    val unitGroups = parse(inputLines).toMutableList()

    runBattle(unitGroups)

    return unitGroups.sumBy { it.nrOfUnits }
}

fun day24b(inputLines: List<String>): Int? {
    var boost = 0
    while (true) {
        val unitGroups = parse(inputLines, boost).toMutableList()

        runBattle(unitGroups)

        if (unitGroups.none { it.type == UnitGroupType.Infection }) {
            return unitGroups.sumBy { it.nrOfUnits }
        }

        boost++
    }
}