import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day13aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day13a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day13a(readInputLines("day13")))
    }
}

class Day13bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day13b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day13b(readInputLines("day13")))
    }
}
