package v2015.days.day22

import java.util.PriorityQueue

private fun parseBossProperties(input: List<String>): Pair<Int, Int> {
    val hitPoints = input[0].split(" ").last().toInt()
    val damage = input[1].split(" ").last().toInt()
    return hitPoints to damage
}

private data class FightStates(
    val isPlayerTurn: Boolean,
    val manaSpent: Int,
    val bossHitPoints: Int,
    val playerHitPoints: Int,
    val playerMana: Int,
    val activeEffects: Map<String, Int> = mapOf()
)

private fun findMinimalManaStrategy(bossBaseHitPoints: Int, bossBaseDamage: Int, playerDamageAtBeginningOfRound: Int = 1): Int {
    val toCheck = PriorityQueue<FightStates> { a, b -> a.manaSpent.compareTo(b.manaSpent)}
    toCheck.add(FightStates(true, 0, bossBaseHitPoints, 50, 500))

    while (toCheck.isNotEmpty()) {
        val fightState = toCheck.poll()

        var playerHitPoints = fightState.playerHitPoints
        var playerMana = fightState.playerMana
        var bossHitPoints = fightState.bossHitPoints
        var activeEffects = fightState.activeEffects.toMutableMap()

        fun isShieldActive(): Boolean = activeEffects.containsKey("shield")
        fun isPoisonActive(): Boolean = activeEffects.containsKey("poison")
        fun isRechargeActive(): Boolean = activeEffects.containsKey("recharge")

        fun isPlayerDead(): Boolean = playerHitPoints <= 0
        fun isBossDead(): Boolean = bossHitPoints <= 0

        if (fightState.isPlayerTurn) {
            playerHitPoints -= playerDamageAtBeginningOfRound
        }
        if (isPlayerDead()) {
            continue
        }

        if (isRechargeActive()) {
            playerMana += 101
        }

        if (isPoisonActive()) {
            bossHitPoints -= 3
        }
        if (isBossDead()) {
            return fightState.manaSpent
        }

        activeEffects =
            activeEffects.mapValues { (_, turns) -> turns - 1 }.filter { (_, turns) -> turns > 0 }.toMutableMap()

        if (fightState.isPlayerTurn) {
            if (playerMana < 53) {
                continue
            }

            // Magic Missile
            if (playerMana >= 53) {
                if (bossHitPoints <= 4) {
                    return fightState.manaSpent + 53
                }
                toCheck.add(
                    FightStates(
                        false,
                        fightState.manaSpent + 53,
                        bossHitPoints - 4,
                        playerHitPoints,
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
                    FightStates(
                        false,
                        fightState.manaSpent + 73,
                        bossHitPoints - 2,
                        playerHitPoints,
                        playerMana - 73,
                        activeEffects
                    )
                )
            }

            // Shield
            if (playerMana >= 113 && !isShieldActive()) {
                toCheck.add(
                    FightStates(
                        false,
                        fightState.manaSpent + 113,
                        bossHitPoints,
                        playerHitPoints,
                        playerMana - 113,
                        activeEffects.plus("shield" to 6)
                    )
                )
            }

            // Poison
            if (playerMana >= 173 && !isPoisonActive()) {
                toCheck.add(
                    FightStates(
                        false,
                        fightState.manaSpent + 173,
                        bossHitPoints,
                        playerHitPoints,
                        playerMana - 173,
                        activeEffects.plus("poison" to 6)
                    )
                )
            }

            // Recharge
            if (playerMana >= 229 && !isRechargeActive()) {
                toCheck.add(
                    FightStates(
                        false,
                        fightState.manaSpent + 229,
                        bossHitPoints,
                        playerHitPoints,
                        playerMana - 229,
                        activeEffects.plus("recharge" to 5)
                    )
                )
            }
        } else {
            val bossDamage = bossBaseDamage - if (isShieldActive()) 7 else 0

            playerHitPoints -= bossDamage

            if (isPlayerDead()) {
                continue
            }

            toCheck.add(
                FightStates(
                    true,
                    fightState.manaSpent,
                    bossHitPoints,
                    playerHitPoints,
                    playerMana,
                    activeEffects
                )
            )
        }
    }

    error("No winning strategy found")
}

fun day22a(input: List<String>): Int {
    val (bossBaseHitPoints, bossBaseDamage) = parseBossProperties(input)
    return findMinimalManaStrategy(bossBaseHitPoints, bossBaseDamage, 0)
}

fun day22b(input: List<String>): Int {
    val (bossBaseHitPoints, bossBaseDamage) = parseBossProperties(input)
    return findMinimalManaStrategy(bossBaseHitPoints, bossBaseDamage)
}
