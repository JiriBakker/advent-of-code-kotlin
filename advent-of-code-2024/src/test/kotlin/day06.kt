import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLines

class Day06aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(41, day06a(listOf(
            "....#.....",
            ".........#",
            "..........",
            "..#.......",
            ".......#..",
            "..........",
            ".#..^.....",
            "........#.",
            "#.........",
            "......#...")))
    }

    @Test
    fun testActualInput() {
        assertEquals(5212, day06a(readInputLines("day06")))
    }
}

class Day06bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(6, day06b(listOf(
            "....#.....",
            ".........#",
            "..........",
            "..#.......",
            ".......#..",
            "..........",
            ".#..^.....",
            "........#.",
            "#.........",
            "......#...")))
    }

    @Test
    @Disabled // Too slow (~7s)
    fun testActualInput() {
        assertEquals(1767, day06b(readInputLines("day06")))
    }
}
