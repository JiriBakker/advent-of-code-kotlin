import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day04aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(13, day04a(listOf(
            "..@@.@@@@.",
            "@@@.@.@.@@",
            "@@@@@.@.@@",
            "@.@@@@..@.",
            "@@.@@@@.@@",
            ".@@@@@@@.@",
            ".@.@.@.@@@",
            "@.@@@.@@@@",
            ".@@@@@@@@.",
            "@.@.@@@.@.")))
    }

    @Test
    fun xtestActualInput() {
        assertEquals(1587, day04a(readInputLines("day04")))
    }
}

class Day04bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(43, day04b(listOf(
            "..@@.@@@@.",
            "@@@.@.@.@@",
            "@@@@@.@.@@",
            "@.@@@@..@.",
            "@@.@@@@.@@",
            ".@@@@@@@.@",
            ".@.@.@.@@@",
            "@.@@@.@@@@",
            ".@@@@@@@@.",
            "@.@.@@@.@.")))
    }

    @Test
    fun testActualInput() {
        assertEquals(8946, day04b(readInputLines("day04")))
    }

}
