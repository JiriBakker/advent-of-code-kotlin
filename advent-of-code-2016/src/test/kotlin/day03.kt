import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test fun testActualInput() {
        assertEquals(869, day03a(readInputLines("day03")))
    }
}

class Day03bTests {
    @Test fun testActualInput() {
        assertEquals(1544, day03b(readInputLines("day03")))
    }
}
