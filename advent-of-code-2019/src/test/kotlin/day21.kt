import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day21aTests {
    @Test fun testActualInput() {
        assertEquals(19347995, day21a(readInputLine("day21", 2019)))
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(1141826552, day21b(readInputLine("day21", 2019)))
    }
}