import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day23a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day23b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day23b(readInputLines("day23")))
    }
}
