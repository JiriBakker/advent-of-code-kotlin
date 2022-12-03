import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test fun testExampleInput1() {
        assertEquals(1120, day14a(listOf(
            "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
            "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."), 1000))
    }

    @Test fun testActualInput() {
        assertEquals(2660, day14a(readInputLines("day14", 2015)))
    }
}

class Day14bTests {
    @Test fun testExampleInput1() {
        assertEquals(689, day14b(listOf(
            "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
            "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."), 1000))
    }

    @Test fun testActualInput() {
        assertEquals(1256, day14b(readInputLines("day14", 2015)))
    }
}