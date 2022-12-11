import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day16aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day16a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day16a(readInputLines("day16")))
    }
}

class Day16bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day16b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day16b(readInputLines("day16")))
    }
}
