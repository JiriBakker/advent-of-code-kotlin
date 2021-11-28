package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day11.day11a
import v2015.days.day11.day11b
import v2015.days.day11.isValidPassword
import util.readInputLine

class Day11aTests {
    @Test fun testExampleInput1() {
        assertEquals(false, isValidPassword("hijklmmn".map { it.code }))
    }

    @Test fun testExampleInput2() {
        assertEquals(false, isValidPassword("abbceffg".map { it.code }))
    }

    @Test fun testExampleInput3() {
        assertEquals(false, isValidPassword("abbcegjk".map { it.code }))
    }

    @Test fun testExampleInput4() {
        assertEquals("abcdffaa", day11a("abcdefgh"))
    }

    @Test fun testExampleInput5() {
        assertEquals("ghjaabcc", day11a("ghijklmn"))
    }

    @Test fun testActualInput() {
        assertEquals("cqjxxyzz", day11a(readInputLine("day11", 2015)))
    }
}

class Day11bTests {
    @Test fun testActualInput() {
        assertEquals("cqkaabcc", day11b(readInputLine("day11", 2015)))
    }
}

