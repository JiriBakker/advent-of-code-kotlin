import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day19aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day19a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day19a(readInputLines("day19")))
    }
}

class Day19bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day19b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day19b(readInputLines("day19")))
    }
}
