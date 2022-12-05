import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines
import util.trimInput

class Day04aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2, day04a(listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8")))
    }

    @Test
    fun testActualInput() {
        assertEquals(536, day04a(readInputLines("day04")))
    }
}

class Day04bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4, day04b(listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8")))
    }

    @Test
    fun testActualInput() {
        assertEquals(845, day04b(readInputLines("day04")))
    }
}
