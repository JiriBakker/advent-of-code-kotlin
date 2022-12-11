import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day20aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day20a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day20a(readInputLines("day20")))
    }
}

class Day20bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day20b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day20b(readInputLines("day20")))
    }
}
