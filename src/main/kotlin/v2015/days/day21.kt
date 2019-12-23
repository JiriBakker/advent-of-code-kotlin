package v2015.days.day21

import v2015.util.combine
import v2015.util.max

private fun parseBossProperties(input: List<String>): Triple<Int, Int, Int> {
    val hitPoints = input[0].split(" ").last().toInt()
    val damage = input[1].split(" ").last().toInt()
    val armor = input[2].split(" ").last().toInt()
    return Triple(hitPoints, damage, armor)
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

fun day21a(input: List<String>): Int {
    val (bossHitPoints, bossDamage, bossArmor) = parseBossProperties(input)

    val gearCombinations = getGearCombinations().map { GearCombination(it) }

    return gearCombinations
        .sortedBy { it.cost }
        .first { willGearWinFight(it, bossHitPoints, bossDamage, bossArmor) }
        .cost
}

fun day21b(input: List<String>): Int {
    val (bossHitPoints, bossDamage, bossArmor) = parseBossProperties(input)

    val gearCombinations = getGearCombinations().map { GearCombination(it) }

    return gearCombinations
        .sortedByDescending { it.cost }
        .first { !willGearWinFight(it, bossHitPoints, bossDamage, bossArmor) }
        .cost
}
