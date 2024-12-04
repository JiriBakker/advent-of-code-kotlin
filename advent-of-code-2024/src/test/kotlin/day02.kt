import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day02aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2, day02a(listOf(
            "7 6 4 2 1",
            "1 2 7 8 9",
            "9 7 6 2 1",
            "1 3 2 4 5",
            "8 6 4 4 1",
            "1 3 6 7 9")))
    }

    @Test
    fun testActualInput() {
        assertEquals(356, day02a(readInputLines("day02")))
    }
}

class Day02bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4, day02b(listOf(
            "7 6 4 2 1",
            "1 2 7 8 9",
            "9 7 6 2 1",
            "1 3 2 4 5",
            "8 6 4 4 1",
            "1 3 6 7 9")))
    }

    @Test
    fun testActualInput() {
        assertEquals(413, day02b(readInputLines("day02")))
    }
}
