import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(110, day23a(listOf(
            "....#..",
            "..###.#",
            "#...#.#",
            ".#...##",
            "#.###..",
            "##.#.##",
            ".#..#..")))
    }

    @Test
    fun testActualInput() {
        assertEquals(4138, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(20, day23b(listOf(
            "....#..",
            "..###.#",
            "#...#.#",
            ".#...##",
            "#.###..",
            "##.#.##",
            ".#..#..")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1010, day23b(readInputLines("day23")))
    }
}
