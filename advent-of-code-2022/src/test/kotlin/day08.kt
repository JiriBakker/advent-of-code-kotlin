import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day08aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day08a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day08a(readInputLines("day08")))
    }
}

class Day08bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day08b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day08b(readInputLines("day08")))
    }
}
