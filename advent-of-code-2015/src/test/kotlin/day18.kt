import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day18a(listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."), 4))
    }

    @Test fun testActualInput() {
        assertEquals(814, day18a(readInputLines("day18", 2015)))
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(17, day18b(listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."), 5))
    }

    @Test fun testActualInput() {
        assertEquals(924, day18b(readInputLines("day18", 2015)))
    }
}