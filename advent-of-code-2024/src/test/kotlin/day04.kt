import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day04aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(18, day04a(listOf(
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX")))
}

    @Test
    fun testActualInput() {
        assertEquals(2378, day04a(readInputLines("day04")))
    }
}

class Day04bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(9, day04b(listOf(
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1796, day04b(readInputLines("day04")))
    }
}
