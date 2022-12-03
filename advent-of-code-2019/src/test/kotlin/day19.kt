import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day19aTests {
    @Test fun testActualInput() {
        assertEquals(164, day19a(readInputLine("day19", 2019)))
    }
}

class Day19bTests {
    @Test fun testActualInput() {
        assertEquals(13081049, day19b(readInputLine("day19", 2019)))
    }
}