package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day10aTests {
    @Test fun testExampleInput1() {
        assertEquals(12, day10a("3,4,1,5", 5))
    }

    @Test fun testActualInput() {
        assertEquals(62238, day10a(readInputLine("day10", 2017)))
    }
}

class Day10bTests {
    @Test fun testExampleInput1() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", day10b(""))
    }

    @Test fun testExampleInput2() {
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", day10b("AoC 2017"))
    }

    @Test fun testExampleInput3() {
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", day10b("1,2,3"))
    }

    @Test fun testExampleInput4() {
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", day10b("1,2,4"))
    }

    @Test fun testActualInput() {
        assertEquals("2b0c9cc0449507a0db3babd57ad9e8d8", day10b(readInputLine("day10", 2017)))
    }
}
