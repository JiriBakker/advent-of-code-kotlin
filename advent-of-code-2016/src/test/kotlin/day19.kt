import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day19a("5"))
    }

    @Test fun testActualInput() {
        assertEquals(1830117, day19a(readInputLine("day19")))
    }
}

class Day19bTests {
    @Test fun testActualInput() {
        assertEquals(1417887, day19b(readInputLine("day19")))
    }
}
