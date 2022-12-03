import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test fun testActualInput() {
        assertEquals(91, day21a(readInputLines("day21", 2015)))
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(158, day21b(readInputLines("day21", 2015)))
    }
}