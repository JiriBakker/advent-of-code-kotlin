import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day12a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day12a(readInputLines("day12")))
    }
}

class Day12bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day12b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day12b(readInputLines("day12")))
    }
}
