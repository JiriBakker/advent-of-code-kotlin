import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(184, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(231, day23b(readInputLines("day23")))
    }
}