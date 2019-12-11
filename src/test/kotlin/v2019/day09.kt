package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day09.day09a
import v2019.days.day09.day09b
import v2019.util.readInputLine

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(109, day09a("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"))
    }

    @Test fun testExampleInput2() {
        assertEquals(1219070632396864, day09a("1102,34915192,34915192,7,4,7,99,0"))
    }

    @Test fun testExampleInput3() {
        assertEquals(1125899906842624, day09a("104,1125899906842624,99"))
    }

    @Test fun testActualInput() {
        assertEquals(3638931938, day09a(readInputLine("day09")))
    }
}

class Day09bTests {
    @Test fun testActualInput() {
        assertEquals(86025, day09b(readInputLine("day09")))
    }
}
