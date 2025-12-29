import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(16, day21a(listOf(
            "...........",
            ".....###.#.",
            ".###.##..#.",
            "..#.#...#..",
            "....#.#....",
            ".##..S####.",
            ".##..#...#.",
            ".......##..",
            ".##.#.####.",
            ".##..##.##.",
            "..........."),
            nrOfSteps = 6))
    }

    @Test
    fun testActualInput() {
        assertEquals(3776, day21a(readInputLines("day21")))
    }
}

class Day21bTests {
    @Test
    fun testActualInput() {
        assertEquals(625587097150084, day21b(readInputLines("day21")))
    }
}
