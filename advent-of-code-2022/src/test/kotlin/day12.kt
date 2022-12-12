import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(31, day12a(listOf(
            "Sabqponm",
            "abcryxxl",
            "accszExk",
            "acctuvwj",
            "abdefghi")))
    }

    @Test
    fun testActualInput() {
        assertEquals(490, day12a(readInputLines("day12")))
    }
}

class Day12bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(29, day12b(listOf(
            "Sabqponm",
            "abcryxxl",
            "accszExk",
            "acctuvwj",
            "abdefghi")))
    }

    @Test
    fun testActualInput() {
        assertEquals(488, day12b(readInputLines("day12")))
    }
}
