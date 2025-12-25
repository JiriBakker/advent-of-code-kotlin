import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day11aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(374, day11a(listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....")))
    }

    @Test
    fun testActualInput() {
        assertEquals(10292708, day11a(readInputLines("day11")))
    }
}

class Day11bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1030, day11b(listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#....."),
            emptyLineDistance = 9))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(8410, day11b(listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#....."),
            emptyLineDistance = 99))
    }

    @Test
    fun testActualInput() {
        assertEquals(790194712336, day11b(readInputLines("day11"), emptyLineDistance = 999_999))
    }

}
