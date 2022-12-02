package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day20a(listOf(
            "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
            "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(144, day20a(readInputLines("day20", 2017)))
    }
}

class Day20bTests {
     @Test fun testExampleInput1() {
        assertEquals(1, day20b(listOf(
            "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
            "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"
        )))
    }

    @Test fun testCustomInput1() {
        assertEquals(1, day20b(listOf(
            "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
            "p=< 4,0,0>, v=<-1,0,0>, a=< 0,0,0>"
        )))
    }

    @Test fun testCustomInput2() {
        assertEquals(0, day20b(listOf(
            "p=<-6,-12,-18>, v=< 3,6,9>, a=< 0,0,0>",
            "p=<-4,-8,-12>, v=< 2,4,6>, a=< 0,0,0>",
            "p=<-2,-4,-6>, v=< 1,2,3>, a=< 0,0,0>",
            "p=< 2,4,6>, v=<-1,-2,-3>, a=< 0,0,0>"
        )))
    }

    @Test fun testCustomInput3() {
        assertEquals(0, day20b(listOf(
            "p=<-8,-14,-20>, v=< 1,1,1>, a=< 1,2,3>",
            "p=< 10,16,22>, v=<-1,-1,-1>, a=< -1,-2,-3>"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(477, day20b(readInputLines("day20", 2017)))
    }
}
