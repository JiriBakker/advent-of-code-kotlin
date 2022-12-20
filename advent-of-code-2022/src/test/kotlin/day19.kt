import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day19aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(33, day19a(listOf(
            "Blueprint 1:" +
            " Each ore robot costs 4 ore." +
            " Each clay robot costs 2 ore." +
            " Each obsidian robot costs 3 ore and 14 clay." +
            " Each geode robot costs 2 ore and 7 obsidian.",
            "Blueprint 2:" +
            " Each ore robot costs 2 ore." +
            " Each clay robot costs 3 ore." +
            " Each obsidian robot costs 3 ore and 8 clay." +
            " Each geode robot costs 3 ore and 12 obsidian.")))
    }

    @Test
    fun testActualInput() {
        assertEquals(2341, day19a(readInputLines("day19")))
    }
}

class Day19bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3472, day19b(listOf(
            "Blueprint 1:" +
            " Each ore robot costs 4 ore." +
            " Each clay robot costs 2 ore." +
            " Each obsidian robot costs 3 ore and 14 clay." +
            " Each geode robot costs 2 ore and 7 obsidian.",
            "Blueprint 2:" +
            " Each ore robot costs 2 ore." +
            " Each clay robot costs 3 ore." +
            " Each obsidian robot costs 3 ore and 8 clay." +
            " Each geode robot costs 3 ore and 12 obsidian.")))
    }

    @Test
    fun testActualInput() {
        assertEquals(3689, day19b(readInputLines("day19")))
    }
}
