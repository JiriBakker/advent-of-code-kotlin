import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day16aTests {
    @Test fun testActualInput() {
        assertEquals(213, day16a(readInputLines("day16", 2015)))
    }
}

class Day16bTests {
    @Test fun testActualInput() {
        assertEquals(323, day16b(readInputLines("day16", 2015)))
    }
}