import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day18aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day18a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day18b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day18b(readInputLines("day18")))
    }
}
