package v2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLines

class Day016Tests {
    @Test
    fun testExampleInput1() {
        assertEquals(5934, day06a(listOf(
            "3,4,3,1,2"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(373378, day06a(readInputLines("day06", 2021)))
    }
}

class Day06bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(26984457539, day06b(listOf(
            "3,4,3,1,2"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(1682576647495, day06b(readInputLines("day06", 2021)))
    }
}
