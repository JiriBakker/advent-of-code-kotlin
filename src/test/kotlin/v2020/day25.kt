package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(14897079L, day25a(listOf(
            "5764801",
            "17807724")))
    }

    @Test fun testActualInput() {
        assertEquals(3803729, day25a(readInputLines("day25", 2020)))
    }
}
