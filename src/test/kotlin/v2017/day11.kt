package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day11aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day11a("ne,ne,ne"))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day11a("ne,ne,sw,sw"))
    }

    @Test fun testExampleInput3() {
        assertEquals(2, day11a("ne,ne,s,s"))
    }

    @Test fun testExampleInput4() {
        assertEquals(3, day11a("se,sw,se,sw,sw"))
    }

    @Test fun testActualInput() {
        assertEquals(685, day11a(readInputLine("day11", 2017)))
    }
}

class Day11bTests {
    @Test fun testActualInput() {
        assertEquals(1457, day11b(readInputLine("day11", 2017)))
    }
}
