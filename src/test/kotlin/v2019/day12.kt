package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(179, day12a(listOf(
            "<x=-1, y=0, z=2>",
            "<x=2, y=-10, z=-7>",
            "<x=4, y=-8, z=8>",
            "<x=3, y=5, z=-1>"), 10))
    }

    @Test fun testExampleInput2() {
        assertEquals(1940, day12a(listOf(
            "<x=-8, y=-10, z=0>",
            "<x=5, y=5, z=10>",
            "<x=2, y=-7, z=3>",
            "<x=9, y=-8, z=-3>"), 100))
    }

    @Test fun testActualInput() {
        assertEquals(10635, day12a(readInputLines("day12", 2019)))
    }
}

class Day12bTests {
    @Test fun testExampleInput1() {
        assertEquals(2772, day12b(listOf(
            "<x=-1, y=0, z=2>",
            "<x=2, y=-10, z=-7>",
            "<x=4, y=-8, z=8>",
            "<x=3, y=5, z=-1>")))
    }

    @Test fun testExampleInput2() {
        assertEquals(4686774924, day12b(listOf(
            "<x=-8, y=-10, z=0>",
            "<x=5, y=5, z=10>",
            "<x=2, y=-7, z=3>",
            "<x=9, y=-8, z=-3>")))
    }

    @Test fun testActualInput() {
        assertEquals(583523031727256, day12b(readInputLines("day12", 2019)))
    }
}