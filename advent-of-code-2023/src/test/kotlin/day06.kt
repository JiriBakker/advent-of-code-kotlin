import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day06aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(288, day06a(listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1413720, day06a(readInputLines("day06")))
    }
}

class Day06bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(71503, day06b(listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200")))
    }

    @Test
    fun testActualInput() {
        assertEquals(30565288, day06b(readInputLines("day06")))
    }

}
