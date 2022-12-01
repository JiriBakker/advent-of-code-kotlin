package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day18a(listOf(
            "set a 1",
            "add a 2",
            "mul a a",
            "mod a 5",
            "snd a",
            "set a 0",
            "rcv a",
            "jgz a -1",
            "set a 1",
            "jgz a -2"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(3423, day18a(readInputLines("day18", 2017)))
    }
}

class Day18bTests {
     @Test fun testExampleInput2() {
        assertEquals(3, day18b(listOf(
            "snd 1",
            "snd 2",
            "snd p",
            "rcv a",
            "rcv b",
            "rcv c",
            "rcv d"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(7493, day18b(readInputLines("day18", 2017)))
    }
}
