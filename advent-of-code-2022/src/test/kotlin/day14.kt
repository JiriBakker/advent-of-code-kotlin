import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day14a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day14a(readInputLines("day14")))
    }
}

class Day14bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day14b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day14b(readInputLines("day14")))
    }
}
