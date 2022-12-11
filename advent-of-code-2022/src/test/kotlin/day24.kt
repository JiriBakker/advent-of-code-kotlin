import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day24aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day24a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day24a(readInputLines("day24")))
    }
}

class Day24bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day24b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day24b(readInputLines("day24")))
    }
}
