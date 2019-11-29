package v2018.days.day23

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

class Day23aTests {
    @Test fun testExampleInput1() {
        assertEquals(7,
            day23a(
                listOf(
                    "pos=<0,0,0>, r=4",
                    "pos=<1,0,0>, r=1",
                    "pos=<4,0,0>, r=3",
                    "pos=<0,2,0>, r=1",
                    "pos=<0,5,0>, r=3",
                    "pos=<0,0,3>, r=1",
                    "pos=<1,1,1>, r=1",
                    "pos=<1,1,2>, r=1",
                    "pos=<1,3,1>, r=1"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(270,
            day23a(readInputLines("day23"))
        )
    }
}

class Day23bTests {
    @Test fun testExampleInput1() {
        assertEquals(36,
            day23b(
                listOf(
                    "pos=<10,12,12>, r=2",
                    "pos=<12,14,12>, r=2",
                    "pos=<16,12,12>, r=4",
                    "pos=<14,14,14>, r=6",
                    "pos=<50,50,50>, r=200",
                    "pos=<10,10,10>, r=5"
                )
            )
        )
    }

    @Test fun testCustomInput1() {
        assertEquals(27,
            day23b(
                listOf(
                    "pos=<10,10,10>, r=3",
                    "pos=<8,8,8>, r=3",
                    "pos=<-6,-6,-6>, r=1"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(106323091,
            day23b(readInputLines("day23"))
        )
    }
}