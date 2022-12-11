import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day25a(listOf(
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day25a(readInputLines("day25")))
    }
}
