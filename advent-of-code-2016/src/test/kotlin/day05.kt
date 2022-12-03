import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLine

class Day05aTests {
    @Disabled // Too slow
    @Test fun testExampleInput1() {
        assertEquals("18f47a30", day05a("abc"))
    }

    @Test fun testActualInput() {
        assertEquals("f97c354d", day05a(readInputLine("day05")))
    }
}

class Day05bTests {
    @Test fun testActualInput() {
        assertEquals("863dde27", day05b(readInputLine("day05")))
    }
}
