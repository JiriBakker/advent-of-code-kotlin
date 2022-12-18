import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day18aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(10, day18a(listOf(
            "1,1,1",
            "2,1,1")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(64, day18a(listOf(
            "2,2,2",
            "1,2,2",
            "3,2,2",
            "2,1,2",
            "2,3,2",
            "2,2,1",
            "2,2,3",
            "2,2,4",
            "2,2,6",
            "1,2,5",
            "3,2,5",
            "2,1,5",
            "2,3,5")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(18, day18a(listOf(
            "1,1,1",
            "2,1,1",
            "1,2,1",
            "1,1,2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(3448, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(58, day18b(listOf(
            "2,2,2",
            "1,2,2",
            "3,2,2",
            "2,1,2",
            "2,3,2",
            "2,2,1",
            "2,2,3",
            "2,2,4",
            "2,2,6",
            "1,2,5",
            "3,2,5",
            "2,1,5",
            "2,3,5")))
    }

    @Test
    fun testActualInput() {
        assertEquals(2052, day18b(readInputLines("day18")))
    }
}
