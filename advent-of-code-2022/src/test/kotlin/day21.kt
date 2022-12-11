import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day21a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day21a(readInputLines("day21")))
    }
}

class Day21bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day21b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day21b(readInputLines("day21")))
    }
}
