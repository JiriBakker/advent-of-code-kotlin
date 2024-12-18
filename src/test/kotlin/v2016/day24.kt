package v2016.days.day24

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(14, day24a(listOf(
            "###########",
            "#0.1.....2#",
            "#.#######.#",
            "#4.......3#",
            "###########"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(462, day24a(readInputLines("day24", 2016)))
    }
}

class Day24bTests {
    @Test fun testActualInput() {
        assertEquals(676, day24b(readInputLines("day24", 2016)))
    }
}
