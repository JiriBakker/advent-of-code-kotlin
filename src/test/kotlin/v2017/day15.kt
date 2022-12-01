package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day15aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day15a(listOf(
            "Generator A starts with 65",
            "Generator B starts with 8921"
        ), 5))
    }

    @Test fun testExampleInput2() {
        assertEquals(588, day15a(listOf(
            "Generator A starts with 65",
            "Generator B starts with 8921"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(638, day15a(readInputLines("day15", 2017)))
    }
}

class Day15bTests {
     @Test fun testExampleInput2() {
        assertEquals(309, day15b(listOf(
            "Generator A starts with 65",
            "Generator B starts with 8921"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(343, day15b(readInputLines("day15", 2017)))
    }
}
