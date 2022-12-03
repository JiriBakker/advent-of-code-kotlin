import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(99, day24a(listOf("1", "2", "3", "4", "5", "7", "8", "9", "10", "11")))
    }

    @Test fun testActualInput() {
        assertEquals(10439961859, day24a(readInputLines("day24")))
    }
}

class Day24bTests {
    @Test fun testExampleInput1() {
        assertEquals(44, day24b(listOf("1", "2", "3", "4", "5", "7", "8", "9", "10", "11")))
    }

    @Test fun testActualInput() {
        assertEquals(72050269, day24b(readInputLines("day24")))
    }
}