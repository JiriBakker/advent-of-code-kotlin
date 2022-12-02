package v2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day04aTests {
    @Test fun testExampleInput1() {
        assertEquals(1514, day04a(listOf(
            "aaaaa-bbb-z-y-x-123[abxyz]",
            "a-b-c-d-e-f-g-h-987[abcde]",
            "not-a-real-room-404[oarel]",
            "totally-real-room-200[decoy]"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(409147, day04a(readInputLines("day04", 2016)))
    }
}

class Day04bTests {
    @Test fun testActualInput() {
        assertEquals(991, day04b(readInputLines("day04", 2016)))
    }
}
