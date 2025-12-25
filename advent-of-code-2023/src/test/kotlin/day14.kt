import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(136, day14a(listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....")))
    }

    @Test
    fun testActualInput() {
        assertEquals(105623, day14a(readInputLines("day14")))
    }
}

class Day14bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(64, day14b(listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....")))
    }

    @Test
    fun testActualInput() {
        assertEquals(98029, day14b(readInputLines("day14")))
    }
}
