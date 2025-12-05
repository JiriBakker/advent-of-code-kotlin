import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day05aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3, day05a(listOf(
            "3-5",
            "10-14",
            "16-20",
            "12-18",
            "",
            "1",
            "5",
            "8",
            "11",
            "17",
            "32")))
    }

    @Test
    fun testActualInput() {
        assertEquals(821, day05a(readInputLines("day05")))
    }
}

class Day05bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(14, day05b(listOf(
            "3-5",
            "10-14",
            "16-20",
            "12-18",
            "",
            "1",
            "5",
            "8",
            "11",
            "17",
            "32")))
    }

    @Test
    fun testActualInput() {
        assertEquals(344771884978261, day05b(readInputLines("day05")))
    }

}
