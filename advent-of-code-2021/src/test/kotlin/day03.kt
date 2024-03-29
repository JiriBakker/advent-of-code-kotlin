import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(198, day03a(listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(3969000, day03a(readInputLines("day03")))
    }
}

class Day03bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(230, day03b(listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(4267809, day03b(readInputLines("day03")))
    }
}
