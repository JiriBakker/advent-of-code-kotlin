package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day12.day12a
import v2015.days.day12.day12b
import util.readInputLine

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
        assertEquals(156366, day12a(readInputLine("day12", 2015)))
    }
}

class Day12bTests {
     @Test fun testExampleInput1() {
        assertEquals(6, day12b("[1,2,3]"))
    }

    @Test fun testExampleInput2() {
        assertEquals(4, day12b("[1,{\"c\":\"red\",\"b\":2},3]"))
    }

    @Test fun testExampleInput3() {
        assertEquals(0, day12b("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"))
    }

    @Test fun testExampleInput4() {
        assertEquals(6, day12b("[1,\"red\",5]"))
    }

    @Test fun testCustomInput1() {
        assertEquals(11, day12b("{\"test\":[{\"a\":\"red\",\"b\":100},10],\"test\":1}"))
    }

    @Test fun testCustomInput2() {
        assertEquals(-11, day12b("{\"test\":[{\"a\":\"red\",\"b\":100},10,-22],\"test\":1}"))
    }

    @Test fun testActualInput() {
        assertEquals(96852, day12b(readInputLine("day12", 2015)))
    }
}

