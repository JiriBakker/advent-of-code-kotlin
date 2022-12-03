import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day08aTests {
    @Test fun testExampleInput1() {
        assertEquals(138, day08a("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"))
    }

    @Test fun testActualInput() {
        assertEquals(36027, day08a(readInputLine("day08", 2018)))
    }
}

class Day08bTests {
    @Test fun testExampleInput1() {
        assertEquals(66, day08b("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"))
    }

    @Test fun testActualInput() {
        assertEquals(23960, day08b(readInputLine("day08", 2018)))
    }
}
