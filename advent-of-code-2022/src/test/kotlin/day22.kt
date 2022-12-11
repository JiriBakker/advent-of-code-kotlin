import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day22aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day22a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day22a(readInputLines("day22")))
    }
}

class Day22bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day22b(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day22b(readInputLines("day22")))
    }
}
