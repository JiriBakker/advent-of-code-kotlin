import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals(1000000, day06a(listOf("turn on 0,0 through 999,999")))
    }

    @Test fun testExampleInput2() {
        assertEquals(999000, day06a(listOf(
            "turn on 0,0 through 999,999",
            "toggle 0,0 through 999,0")))
    }

    @Test fun testExampleInput3() {
        assertEquals(998996, day06a(listOf(
            "turn on 0,0 through 999,999",
            "toggle 0,0 through 999,0",
            "turn off 499,499 through 500,500")))
    }

    @Test fun testActualInput() {
        assertEquals(543903, day06a(readInputLines("day06")))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day06b(listOf("turn on 0,0 through 0,0")))
    }

    @Test fun testExampleInput2() {
        assertEquals(2000000, day06b(listOf("toggle 0,0 through 999,999")))
    }

    @Test fun testActualInput() {
        assertEquals(14687245, day06b(readInputLines("day06")))
    }
}
