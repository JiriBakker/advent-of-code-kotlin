import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day19aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(6, day19a(listOf(
            "r, wr, b, g, bwu, rb, gb, br",
            "",
            "brwrr",
            "bggr",
            "gbbr",
            "rrbgbr",
            "ubwu",
            "bwurrg",
            "brgr",
            "bbrgwb")))
    }

    @Test
    fun testActualInput() {
        assertEquals(317, day19a(readInputLines("day19")))
    }
}

class Day19bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(16, day19b(listOf(
            "r, wr, b, g, bwu, rb, gb, br",
            "",
            "brwrr",
            "bggr",
            "gbbr",
            "rrbgbr",
            "ubwu",
            "bwurrg",
            "brgr",
            "bbrgwb")))
    }

    @Test
    fun testActualInput() {
        assertEquals(883443544805484, day19b(readInputLines("day19")))
    }
}
