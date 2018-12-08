package days.day06

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals(17, day06a(listOf("1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9")))
    }

    @Test fun testWithInfiniteAsLargest() {
        assertEquals(15, day06a(listOf("2, 1", "3, 6", "8, 5", "6, 6", "8, 9")))
    }

    @Test fun testActualInput() {
        assertEquals(2917, day06a(readInputLines("day06")))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals(16, day06b(listOf("1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9"), 32))
    }

    @Test fun testActualInput() {
        assertEquals(44202, day06b(readInputLines("day06")))
    }
}
