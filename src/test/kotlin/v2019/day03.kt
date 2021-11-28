package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day03aTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day03a(listOf("R8,U5,L5,D3", "U7,R6,D4,L4")))
    }

    @Test fun testExampleInput2() {
        assertEquals(159, day03a(listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")))
    }

    @Test fun testExampleInput3() {
        assertEquals(135, day03a(listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")))
    }

    @Test fun testActualInput() {
        assertEquals(1017, day03a(readInputLines("day03", 2019)))
    }
}

class Day03bTests {
    @Test fun testExampleInput1() {
        assertEquals(30, day03b(listOf("R8,U5,L5,D3", "U7,R6,D4,L4")))
    }

    @Test fun testExampleInput2() {
        assertEquals(610, day03b(listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")))
    }

    @Test fun testExampleInput3() {
        assertEquals(410, day03b(listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")))
    }

    @Test fun testActualInput() {
        assertEquals(11432, day03b(readInputLines("day03", 2019)))
    }
}
