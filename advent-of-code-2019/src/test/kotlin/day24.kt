import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day24aTests {
    @Test fun testActualInput() {
        assertEquals(32776479, day24a(readInputLines("day24", 2019)))
    }
}

class Day24bTests {
    @Test fun testExampleInput1() {
        assertEquals(99, day24b(listOf(
            "....#",
            "#..#.",
            "#.?##",
            "..#..",
            "#...."), 10))
    }

    @Test fun testActualInput() {
        assertEquals(2017, day24b(readInputLines("day24", 2019)))
    }
}