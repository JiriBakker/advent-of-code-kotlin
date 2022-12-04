import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines
import util.trimInput

class Day05aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day05a(listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day05a(readInputLines("day05")))
    }
}

class Day05bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day05b(
            """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8
            """.trimInput()))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day05b(readInputLines("day05")))
    }
}
