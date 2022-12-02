package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day09a("{}"))
    }

    @Test fun testExampleInput2() {
        assertEquals(6, day09a("{{{}}}"))
    }

    @Test fun testExampleInput3() {
        assertEquals(5, day09a("{{},{}}"))
    }

    @Test fun testExampleInput4() {
        assertEquals(16, day09a("{{{},{},{{}}}}"))
    }

    @Test fun testExampleInput5() {
        assertEquals(1, day09a("{<a>,<a>,<a>,<a>}"))
    }

    @Test fun testExampleInput6() {
        assertEquals(9, day09a("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
    }

    @Test fun testExampleInput7() {
        assertEquals(9, day09a("{{<!!>},{<!!>},{<!!>},{<!!>}}"))
    }

    @Test fun testExampleInput8() {
        assertEquals(3, day09a("{{<a!>},{<a!>},{<a!>},{<ab>}}"))
    }

    @Test fun testActualInput() {
        assertEquals(11347, day09a(readInputLine("day09", 2017)))
    }
}

class Day09bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day09b("<>"))
    }

    @Test fun testExampleInput2() {
        assertEquals(17, day09b("<random characters>"))
    }

    @Test fun testExampleInput3() {
        assertEquals(3, day09b("<<<<>"))
    }

    @Test fun testExampleInput4() {
        assertEquals(2, day09b("<{!>}>"))
    }

    @Test fun testExampleInput5() {
        assertEquals(0, day09b("<!!>"))
    }

    @Test fun testExampleInput6() {
        assertEquals(0, day09b("<!!!>>"))
    }

    @Test fun testExampleInput7() {
        assertEquals(10, day09b("<{o\"i!a,<{i<a>"))
    }

    @Test fun testActualInput() {
        assertEquals(5404, day09b(readInputLine("day09", 2017)))
    }
}
