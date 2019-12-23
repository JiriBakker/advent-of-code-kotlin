package v2015.days.day22

import v2015.util.combine
import kotlin.math.max
import kotlin.math.min
import java.util.PriorityQueue

private fun parseBossProperties(input: List<String>): Pair<Int, Int> {
    val hitPoints = input[0].split(" ").last().toInt()
    val damage = input[1].split(" ").last().toInt()
    return hitPoints to damage
}

private data class Gear(val name: String, val cost: Int, val damage: Int, val armor: Int)

private data class GearCombination(val gear: List<Gear>) {
    val cost = gear.sumBy { it.cost }
    val damage = gear.sumBy { it.damage }
    val armor = gear.sumBy { it.armor }
}

private val weapons = listOf(
    Gear("Dagger", 8, 4, 0), // 2
    Gear("Shortsword", 10, 5, 0), // 2
    Gear("Warhammer", 25, 6, 0), // 4.2
    Gear("Longsword", 40, 7, 0), // 5.7
    Gear("Greataxe", 74, 8, 0) // 9.3
)

private val armor = listOf(
    Gear("Leather", 13, 0, 1), // 13
    Gear("Chainmail", 31, 0, 2), // 15.5
    Gear("Splintmail", 53, 0, 3), // 17.6
    Gear("Bandedmail", 75, 0, 4), // 18.8
    Gear("Platemail", 102, 0, 5) // 20.4
)

private val rings = listOf(
    Gear("Damage +1", 25, 1, 0), // 25
    Gear("Damage +2", 50, 2, 0), // 25
    Gear("Damage +3", 100, 3, 0), // 33.3
    Gear("Defense +1", 20, 0, 1), // 20
    Gear("Defense +2", 40, 0, 2), // 20
    Gear("Defense +3", 80, 0, 3) // 26.7
)

private fun getGearCombinations(): Sequence<List<Gear>> {
    return sequence {
        for (weapon in weapons) {
            yield(listOf(weapon))
            for (armorItem in armor) {
                yield(listOf(weapon, armorItem))
                for (ring in rings) {
                    yield(listOf(weapon, ring))
                    yield(listOf(weapon, armorItem, ring))
                }
                rings.combine(2)
                    .filter { it[0] != it[1] }
                    .forEach { (ring1, ring2) ->
                        yield(listOf(weapon, ring1, ring2))
                        yield(listOf(weapon, armorItem, ring1, ring2))
                    }
            }
        }
    }
}

private fun willGearWinFight(gear: GearCombination, bossBaseHitPoints: Int, bossBaseDamage: Int, bossBaseArmor: Int): Boolean {
    val playerDamage = max(1, gear.damage - bossBaseArmor)
    val bossDamage = max(1, bossBaseDamage - gear.armor)

    return (100 / bossDamage) >=  (bossBaseHitPoints / playerDamage)
}


private data class FightState(
    val manaSpent: Int,
    val bossHitPoints: Int,
    val playerHitPoints: Int,
    val playerMana: Int,
    val activeEffects: List<Pair<String, Int>> = listOf()
)

fun day22a(input: List<String>): Int {
    val (bossBaseHitPoints, bossBaseDamage) = parseBossProperties(input)

    val toCheck = PriorityQueue<FightState> { a, b -> a.manaSpent.compareTo(b.manaSpent) * 100 + b.playerHitPoints.compareTo(a.playerHitPoints) * 10 +  a.bossHitPoints.compareTo(b.bossHitPoints) }
    toCheck.add(FightState(0, bossBaseHitPoints, 50, 500))

    while (toCheck.isNotEmpty()) {
        val fightState = toCheck.poll()

        if (toCheck.size < 50) {
            val a = 0
        }

        if (fightState.playerHitPoints <= 0) {
            continue
        }

        var playerMana = fightState.playerMana
        val rechargeEffect = fightState.activeEffects.firstOrNull { it.first == "recharge" }?.second ?: 0
        playerMana += 101 * min(2, rechargeEffect)

        var bossHitPoints = fightState.bossHitPoints
        val poisonEffect = fightState.activeEffects.firstOrNull { it.first == "poison" }?.second ?: 0
        bossHitPoints -= 3 * min(2, poisonEffect)

        if (bossHitPoints <= 0) {
            return fightState.manaSpent
        }
        if (playerMana < 53) {
            continue
        }

        val activeEffects = fightState.activeEffects.toMutableList().mapNotNull { if (it.second > 2) Pair(it.first, it.second - 2) else null }

        val bossDamage = max(1, bossBaseDamage - (if (activeEffects.any { it.first == "shield" }) 7 else 0))

        // Magic Missile
        if (playerMana >= 53) {
            if (bossHitPoints <= 4) {
                return fightState.manaSpent + 53
            }
            toCheck.add(
                FightState(
                    fightState.manaSpent + 53,
                    bossHitPoints - 4,
                    fightState.playerHitPoints - bossDamage,
                    playerMana - 53,
                    activeEffects
                )
            )
        }

        // Drain
        if (playerMana >= 73) {
            if (bossHitPoints <= 2) {
                return fightState.manaSpent + 73
            }
            toCheck.add(
                FightState(
                    fightState.manaSpent + 73,
                    bossHitPoints - 2,
                    fightState.playerHitPoints - bossDamage + 2,
                    playerMana - 73,
                    activeEffects
                )
            )
        }

        // Shield
        if (playerMana >= 113 && activeEffects.none { it.first == "shield" }) {
            toCheck.add(
                FightState(
                    fightState.manaSpent + 113,
                    bossHitPoints,
                    fightState.playerHitPoints - bossDamage + 7,
                    playerMana - 113,
                    activeEffects.plus("shield" to 6)
                )
            )
        }

        // Poison
        if (playerMana >= 173 && activeEffects.none { it.first == "poison" }) {
            toCheck.add(
                FightState(
                    fightState.manaSpent + 173,
                    bossHitPoints,
                    fightState.playerHitPoints - bossDamage,
                    playerMana - 173,
                    activeEffects.plus("poison" to 6)
                )
            )
        }

        // Recharge
        if (playerMana >= 229 && activeEffects.none { it.first == "recharge" }) {
            toCheck.add(
                FightState(
                    fightState.manaSpent + 229,
                    bossHitPoints,
                    fightState.playerHitPoints - bossDamage,
                    playerMana - 229,
                    activeEffects.plus("recharge" to 5)
                )
            )
        }
    }

    error("No winning strategy found")
}

fun day22b(input: List<String>): Int {
   return 0
}
