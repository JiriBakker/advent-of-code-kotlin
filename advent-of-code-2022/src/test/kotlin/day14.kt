import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(24, day14a(listOf(
            "498,4 -> 498,6 -> 496,6",
            "503,4 -> 502,4 -> 502,9 -> 494,9")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1078, day14a(readInputLines("day14")))
    }
}

class Day14bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(93, day14b(listOf(
            "498,4 -> 498,6 -> 496,6",
            "503,4 -> 502,4 -> 502,9 -> 494,9")))
    }

    @Test
    fun testActualInput() {
        assertEquals(30157, day14b(readInputLines("day14")))
    }
}
