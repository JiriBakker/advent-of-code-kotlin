import util.sumOfLong

fun day13a(input: List<String>) =
    input
        .parseClawMachines()
        .sumLowestCosts()

fun day13b(input: List<String>) =
    input
        .parseClawMachines(10000000000000L)
        .sumLowestCosts()

private class ClawMachine(
    val dxA: Long,
    val dyA: Long,
    val dxB: Long,
    val dyB: Long,
    val prizeX: Long,
    val prizeY: Long
) {

    val lowestCost: Long? by lazy {
        // Find line intersection of dxA*a + dxB*b = prizeX and dyA*a + dyB*b = prizeY
        val determinant = dxA * dyB - dxB * dyA
        if (determinant == 0L) {
            null
        } else {
            val determinantX = prizeX * dyB - dxB * prizeY
            val determinantY: Long = dxA * prizeY - prizeX * dyA

            val a = determinantX / determinant
            val b = determinantY / determinant

            if (dxA * a + dxB * b == prizeX && dyA * a + dyB * b == prizeY) {
                a * 3 + b
            } else {
                null
            }
        }
    }
}

private fun List<String>.parseClawMachines(prizeOffset: Long = 0L) =
    this.chunked(4)
        .map { lines ->
            val buttonA = lines[0]
            val buttonB = lines[1]
            val prize = lines[2]

            val dxA = buttonA.split(" ")[2].substring(2).trimEnd(',').toLong()
            val dyA = buttonA.split(" ")[3].substring(2).toLong()
            val dxB = buttonB.split(" ")[2].substring(2).trimEnd(',').toLong()
            val dyB = buttonB.split(" ")[3].substring(2).toLong()
            val prizeX = prize.split(" ")[1].substring(2).trimEnd(',').toLong() + prizeOffset
            val prizeY = prize.split(" ")[2].substring(2).toLong() + prizeOffset

            ClawMachine(dxA, dyA, dxB, dyB, prizeX, prizeY)
        }

private fun List<ClawMachine>.sumLowestCosts() =
    this.sumOfLong { clawMachine -> clawMachine.lowestCost ?: 0 }
