package v2021

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day17aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(45, day17a(listOf(
            "target area: x=20..30, y=-10..-5"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(9180, day17a(readInputLines("day17", 2021)))
    }
}

class Day17bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(112, day17b(listOf(
            "target area: x=20..30, y=-10..-5"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(3767, day17b(readInputLines("day17", 2021)))
    }
}
