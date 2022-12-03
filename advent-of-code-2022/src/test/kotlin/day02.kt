import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day02aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(15, day02a(listOf(
            "A Y",
            "B X",
            "C Z")))
    }

    @Test
    fun testActualInput() {
        assertEquals(10718, day02a(readInputLines("day02")))
    }
}

class Day02bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(12, day02b(listOf(
            "A Y",
            "B X",
            "C Z")))
    }

    @Test
    fun testActualInput() {
        assertEquals(14652, day02b(readInputLines("day02")))
    }
}
