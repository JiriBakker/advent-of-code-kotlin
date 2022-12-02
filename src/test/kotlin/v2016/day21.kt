package v2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test fun testExampleInput1() {
        assertEquals("ebcda", day21a(listOf("swap position 4 with position 0"), "abcde"))
    }

    @Test fun testExampleInput2() {
        assertEquals("edcba", day21a(listOf("swap letter d with letter b"), "ebcda"))
    }

    @Test fun testExampleInput3() {
        assertEquals("abcde", day21a(listOf("reverse positions 0 through 4"), "edcba"))
    }

    @Test fun testExampleInput4() {
        assertEquals("bcdea", day21a(listOf("rotate left 1 step"), "abcde"))
    }

    @Test fun testExampleInput5() {
        assertEquals("bdeac", day21a(listOf("move position 1 to position 4"), "bcdea"))
    }

    @Test fun testExampleInput6() {
        assertEquals("abdec", day21a(listOf("move position 3 to position 0"), "bdeac"))
    }

    @Test fun testExampleInput7() {
        assertEquals("ecabd", day21a(listOf("rotate based on position of letter b"), "abdec"))
    }

    @Test fun testExampleInput8() {
        assertEquals("decab", day21a(listOf("rotate based on position of letter d"), "ecabd"))
    }

    @Test fun testPartBInput() {
        assertEquals("fbgdceah", day21a(readInputLines("day21", 2016), "egcdahbf"))
    }

    @Test fun testActualInput() {
        assertEquals("aefgbcdh", day21a(readInputLines("day21", 2016)))
    }
}

class Day21bTests {
    @Test fun testExampleInput1() {
        assertEquals("abcde", day21b(listOf("swap position 4 with position 0"), "ebcda"))
    }

    @Test fun testExampleInput2() {
        assertEquals("ebcda", day21b(listOf("swap letter d with letter b"), "edcba"))
    }

    @Test fun testExampleInput3() {
        assertEquals("edcba", day21b(listOf("reverse positions 0 through 4"), "abcde"))
    }

    @Test fun testExampleInput4() {
        assertEquals("abcde", day21b(listOf("rotate left 1 step"), "bcdea"))
    }

    @Test fun testExampleInput5() {
        assertEquals("bcdea", day21b(listOf("move position 1 to position 4"), "bdeac"))
    }

    @Test fun testExampleInput6() {
        assertEquals("bdeac", day21b(listOf("move position 3 to position 0"), "abdec"))
    }

    @Test fun testExampleInput7() {
        assertEquals("abdec", day21b(listOf("rotate based on position of letter b"), "ecabd"))
    }

    @Test fun testExampleInput8() {
        assertEquals("ecabd", day21b(listOf("rotate based on position of letter d"), "decab"))
    }

    @Test fun testPartAInput() {
        assertEquals("abcdefgh", day21b(readInputLines("day21", 2016), "aefgbcdh"))
    }

    @Test fun testActualInput() {
        assertEquals("egcdahbf", day21b(readInputLines("day21", 2016)))
    }
}
