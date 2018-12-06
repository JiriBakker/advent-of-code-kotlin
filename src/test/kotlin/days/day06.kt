package days

import org.junit.Assert.assertEquals
import org.junit.Test

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals(17, day06a(listOf("1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9")))
    }

    @Test fun testWithInfiniteAsLargest() {
        assertEquals(15, day06a(listOf("2, 1", "3, 6", "8, 5", "6, 6", "8, 9")))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals(16, day06b(listOf("1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9"), 32))
    }
}


