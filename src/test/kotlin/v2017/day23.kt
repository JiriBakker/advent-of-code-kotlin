package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(8281, day23a(readInputLines("day23", 2017)))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(911, day23b(readInputLines("day23", 2017)))
    }
}
