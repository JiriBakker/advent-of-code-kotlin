import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day17aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day17a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day17a(readInputLines("day17")))
    }
}

class Day17bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day17b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day17b(readInputLines("day17")))
    }
}
