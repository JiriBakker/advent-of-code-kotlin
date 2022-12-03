import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day05aTests {

    @Test fun testExampleInput1() {
        assertEquals(357, day05a(listOf(
            "FBFBBFFRLR")))
    }

    @Test fun testExampleInput2() {
        assertEquals(820, day05a(listOf(
            "BFFFBBFRRR",
            "FFFBBBFRRR",
            "BBFFBBFRLL")))
    }

    @Test fun testActualInput() {
        assertEquals(991, day05a(readInputLines("day05")))
    }
}

class Day05bTests {

    @Test fun testActualInput() {
        assertEquals(534, day05b(readInputLines("day05")))
    }
}
