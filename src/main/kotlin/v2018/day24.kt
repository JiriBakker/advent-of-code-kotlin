package v2018

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
    var nrOfUnits = initialNrOfUnits
        private set

    val effectivePower
        get() = nrOfUnits * attackDamage

    fun receiveAttack(attacker: UnitGroup): Int {
        val damage = computeDamageForAttack(attacker)
        val unitsKilled = damage / hitPointsPerUnit
        nrOfUnits = Math.max(0, nrOfUnits - unitsKilled)
        return unitsKilled
    }

    fun computeDamageForAttack(attacker: UnitGroup): Int {
        return when (attacker.attackType) {
            in immunities -> 0
            in weaknesses -> 2 * attacker.effectivePower
            else -> attacker.effectivePower
        }
    }

    val id
        get() = "${if (type == UnitGroupType.Infection) "inf" else "imm"}-$groupNr"

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

private fun selectTargets(unitGroups: List<UnitGroup>): Sequence<Pair<UnitGroup, UnitGroup>> {
    val potentialAttackers = unitGroups.toMutableList()
    val potentialDefenders = unitGroups.toMutableList()

    fun getNextAttacker(): UnitGroup? {
        return potentialAttackers
            .maxWithOrNull(
                compareBy<UnitGroup> { it.effectivePower }
                    .thenBy { it.initiative }
            )
    }

    fun findOptimalTarget(attacker: UnitGroup): UnitGroup? {
        return potentialDefenders
            .filter { it.type != attacker.type }
            .filter { it.computeDamageForAttack(attacker) > 0 }
            .maxWithOrNull(
                compareBy<UnitGroup> { it.computeDamageForAttack(attacker) }
                    .thenBy { it.effectivePower }
                    .thenBy { it.initiative }
            )
    }

    return sequence {
        while (potentialAttackers.isNotEmpty() && potentialDefenders.isNotEmpty()) {
            val attacker = getNextAttacker()!!
            potentialAttackers.remove(attacker)

            val defender = findOptimalTarget(attacker) ?: continue
            potentialDefenders.remove(defender)

            yield(Pair(attacker, defender))
        }
    }
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

            val unitsKilled = defender.receiveAttack(attacker)
            if (defender.nrOfUnits == 0) {
                unitGroups.remove(defender)
            }

            anyKilled = anyKilled || unitsKilled > 0
        }
    } while (!isFightOver() && anyKilled)
}

fun day24a(inputLines: List<String>): Int {
    val unitGroups = parse(inputLines).toMutableList()

    runBattle(unitGroups)

    return unitGroups.sumOf { it.nrOfUnits }
}

fun day24b(inputLines: List<String>): Int {
    var boost = 0
    while (true) {
        val unitGroups = parse(inputLines, boost).toMutableList()

        runBattle(unitGroups)

        if (unitGroups.none { it.type == UnitGroupType.Infection }) {
            return unitGroups.sumOf { it.nrOfUnits }
        }

        boost++
    }
}