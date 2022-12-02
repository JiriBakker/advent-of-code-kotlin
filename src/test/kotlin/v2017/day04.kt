package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day04aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day04a(listOf("aa bb cc dd ee")))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day04a(listOf("aa bb cc dd aa")))
    }

    @Test fun testExampleInput3() {
        assertEquals(1, day04a(listOf("aa bb cc dd aaa")))
    }

    @Test fun testActualInput() {
        assertEquals(325, day04a(readInputLines("day04", 2017)))
    }
}

class Day04bTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day04b(listOf("abcde fghij")))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day04b(listOf("abcde xyz ecdab")))
    }

    @Test fun testExampleInput3() {
        assertEquals(1, day04b(listOf("a ab abc abd abf abj")))
    }

    @Test fun testExampleInput4() {
        assertEquals(1, day04b(listOf("iiii oiii ooii oooi oooo")))
    }

    @Test fun testExampleInput5() {
        assertEquals(0, day04b(listOf("oiii ioii iioi iiio")))
    }

    @Test fun testActualInput() {
        assertEquals(119, day04b(readInputLines("day04", 2017)))
    }
}
