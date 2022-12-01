package v2018

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(5216,
            day24a(
                listOf(
                    "Immune System:",
                    "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
                    "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3",
                    "",
                    "Infection:",
                    "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
                    "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
                )
            )
        )
    }

    @Test fun testCustomInput1() {
        assertEquals(7,
            day24a(
                listOf(
                    "Immune System:",
                    "10 units each with 5 hit points with an attack that does 1 cold damage at initiative 1",
                    "",
                    "Infection:",
                    "7 units each with 10 hit points with an attack that does 5 fire damage at initiative 2"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(22859,
            day24a(readInputLines("day24", 2018))
        )
    }
}

class Day24BTests {
    @Test fun testExampleInput1() {
        assertEquals(51,
            day24b(
                listOf(
                    "Immune System:",
                    "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
                    "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3",
                    "",
                    "Infection:",
                    "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
                    "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(2834,
            day24b(readInputLines("day24", 2018))
        )
    }
}