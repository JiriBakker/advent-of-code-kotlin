import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day01aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(11, day01a(listOf(
            "3   4",
            "4   3",
            "2   5",
            "1   3",
            "3   9",
            "3   3")))
    }

    @Test
    fun testActualInput() {
        assertEquals(2113135, day01a(readInputLines("day01")))
    }
}

class Day01bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(31, day01b(listOf(
            "3   4",
            "4   3",
            "2   5",
            "1   3",
            "3   9",
            "3   3")))
    }

    @Test
    fun testActualInput() {
        assertEquals(19097157, day01b(readInputLines("day01")))
    }
}
