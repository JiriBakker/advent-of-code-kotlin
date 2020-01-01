package v2016.days.day17

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day17aTests {
    @Test fun testExampleInput1() {
        assertEquals("DDRRRD", day17a("ihgpwlah"))
    }

    @Test fun testExampleInput2() {
        assertEquals("DDUDRLRRUDRD", day17a("kglvqrro"))
    }

    @Test fun testExampleInput3() {
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", day17a("ulqzkmiv"))
    }

    @Test fun testActualInput() {
        assertEquals("RLRDRDUDDR", day17a(readInputLine("day17", 2016)))
    }
}

class Day17bTests {
    @Test fun testActualInput() {
        assertEquals(420, day17b(readInputLine("day17", 2016)))
    }
}
