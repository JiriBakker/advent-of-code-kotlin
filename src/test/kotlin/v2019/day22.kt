package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day22.day22a
import v2019.days.day22.day22b
import util.readInputLines

class Day22aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day22a(listOf(
            "deal with increment 7",
            "deal into new stack",
            "deal into new stack"), 10, 9))
    }

    @Test fun testExampleInput2() {
        assertEquals(8, day22a(listOf(
            "cut 6",
            "deal with increment 7",
            "deal into new stack"), 10, 9))
    }

    @Test fun testExampleInput3() {
        assertEquals(9, day22a(listOf(
            "deal with increment 7",
            "deal with increment 9",
            "cut -2"), 10, 9))
    }

    @Test fun testExampleInput4() {
        assertEquals(0, day22a(listOf(
            "deal into new stack",
            "cut -2",
            "deal with increment 7",
            "cut 8",
            "cut -4",
            "deal with increment 7",
            "cut 3",
            "deal with increment 9",
            "deal with increment 3",
            "cut -1"), 10, 9))
    }

    @Test fun testActualInput() {
        assertEquals(2604, day22a(readInputLines("day22", 2019)))
    }
}

class Day22bTests {
    @Test fun testActualInputForPartA() {
        assertEquals(2019, day22b(readInputLines("day22", 2019), 10007, 2604, 1))
    }

    @Test fun testActualInput() {
        assertEquals(79608410258462, day22b(readInputLines("day22", 2019)))
    }
}