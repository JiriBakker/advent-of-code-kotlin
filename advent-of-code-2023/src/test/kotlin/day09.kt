import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day09aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(114, day09a(listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1904165718, day09a(readInputLines("day09")))
    }
}

class Day09bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2, day09b(listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45")))
    }

    @Test
    fun testActualInput() {
        assertEquals(964, day09b(readInputLines("day09")))
    }

}
