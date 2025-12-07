import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day07aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(21, day07a(listOf(
            ".......S.......",
            "...............",
            ".......^.......",
            "...............",
            "......^.^......",
            "...............",
            ".....^.^.^.....",
            "...............",
            "....^.^...^....",
            "...............",
            "...^.^...^.^...",
            "...............",
            "..^...^.....^..",
            "...............",
            ".^.^.^.^.^...^.",
            "...............")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1587, day07a(readInputLines("day07")))
    }
}

class Day07bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(40, day07b(listOf(
            ".......S.......",
            "...............",
            ".......^.......",
            "...............",
            "......^.^......",
            "...............",
            ".....^.^.^.....",
            "...............",
            "....^.^...^....",
            "...............",
            "...^.^...^.^...",
            "...............",
            "..^...^.....^..",
            "...............",
            ".^.^.^.^.^...^.",
            "...............")))
    }

    @Test
    fun testActualInput() {
        assertEquals(5748679033029, day07b(readInputLines("day07")))
    }

}
