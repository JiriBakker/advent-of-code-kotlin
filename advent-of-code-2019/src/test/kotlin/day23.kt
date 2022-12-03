import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(19473, day23a(readInputLine("day23")))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(12475, day23b(readInputLine("day23")))
    }
}