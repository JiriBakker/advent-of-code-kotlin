package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day12.day12a
import v2015.days.day12.day12b
import v2015.util.readInputLine

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day12a("[1,2,3]"))
    }

    @Test fun testExampleInput2() {
        assertEquals(6, day12a("{\"a\":2,\"b\":4}"))
    }

    @Test fun testExampleInput3() {
        assertEquals(3, day12a("[[[3]]]"))
    }

    @Test fun testExampleInput4() {
        assertEquals(3, day12a("{\"a\":{\"b\":4},\"c\":-1}"))
    }

    @Test fun testExampleInput5() {
        assertEquals(0, day12a("{\"a\":[-1,1]}"))
    }

    @Test fun testExampleInput6() {
        assertEquals(0, day12a("[-1,{\"a\":1}]"))
    }

    @Test fun testExampleInput7() {
        assertEquals(0, day12a("[]"))
    }

    @Test fun testExampleInput8() {
        assertEquals(0, day12a("{}"))
    }

    @Test fun testActualInput() {
        assertEquals(119433, day12a(readInputLine("day12")))
    }
}

class Day12bTests {
    @Test fun testActualInput() {
        assertEquals(30265, day12b(readInputLine("day12")))
    }
}

