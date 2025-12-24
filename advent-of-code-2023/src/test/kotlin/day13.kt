import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day13aTests {

    @Test
    fun testExampleInput1() {
        assertEquals(405, day13a(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
            "",
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#")))
    }

    @Test
    fun testActualInput() {
        assertEquals(43614, day13a(readInputLines("day13")))
    }
}

class Day13bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(400, day13b(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
            "",
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#")))
    }

    @Test
    fun testActualInput() {
        assertEquals(36771, day13b(readInputLines("day13")))
    }

}
