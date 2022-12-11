import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day15a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day15a(readInputLines("day15")))
    }
}

class Day15bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day15b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day15b(readInputLines("day15")))
    }
}
