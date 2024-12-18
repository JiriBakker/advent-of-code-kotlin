import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLines

class Day18aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(22, day18a(listOf(
            "5,4",
            "4,2",
            "4,5",
            "3,0",
            "2,1",
            "6,3",
            "2,4",
            "1,5",
            "0,6",
            "3,3",
            "2,6",
            "5,1",
            "1,2",
            "5,5",
            "2,5",
            "6,5",
            "1,4",
            "0,4",
            "6,4",
            "1,1",
            "6,1",
            "1,0",
            "0,5",
            "1,6",
            "2,0"), nrOfBytesToFall = 12))
    }

    @Test
    fun testActualInput() {
        assertEquals(336, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test
    fun testExampleInput1() {
        assertEquals("6,1", day18b(listOf(
            "5,4",
            "4,2",
            "4,5",
            "3,0",
            "2,1",
            "6,3",
            "2,4",
            "1,5",
            "0,6",
            "3,3",
            "2,6",
            "5,1",
            "1,2",
            "5,5",
            "2,5",
            "6,5",
            "1,4",
            "0,4",
            "6,4",
            "1,1",
            "6,1",
            "1,0",
            "0,5",
            "1,6",
            "2,0")))
    }

    @Test
    @Disabled // Too slow
    fun testActualInput() {
        assertEquals("24,30", day18b(readInputLines("day18")))
    }
}
