package v2021

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day15aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(40, day15a(listOf(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(811, day15a(readInputLines("day15", 2021)))
    }
}

class Day15bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(315, day15b(listOf(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(3012, day15b(readInputLines("day15", 2021)))
    }
}
