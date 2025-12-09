import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day09aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(50, day09a(listOf(
            "7,1",
            "11,1",
            "11,7",
            "9,7",
            "9,5",
            "2,5",
            "2,3",
            "7,3")))
    }

    @Test
    fun testActualInput() {
        assertEquals(24360, day09a(readInputLines("day09")))
    }
}

class Day09bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(24, day09b(listOf(
            "7,1",
            "11,1",
            "11,7",
            "9,7",
            "9,5",
            "2,5",
            "2,3",
            "7,3")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1613305596,
            day09b(
                readInputLines("day09"),
                xRequirement = 94671 // Use knowledge of input data to limit search scope (otherwise it take 15-20 seconds to find answer)
            )
        )
    }

}
