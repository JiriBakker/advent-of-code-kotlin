import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine
import Day11Utils.isValidPassword

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
        assertEquals("cqjxxyzz", day11a(readInputLine("day11")))
    }
}

class Day11bTests {
    @Test fun testActualInput() {
        assertEquals("cqkaabcc", day11b(readInputLine("day11")))
    }
}

