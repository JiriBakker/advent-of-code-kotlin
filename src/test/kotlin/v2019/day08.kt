package v2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day08aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day08a("123456789012", 3, 2))
    }

    @Test fun testActualInput() {
        assertEquals(1474, day08a(readInputLine("day08", 2019)))
    }
}

class Day08bTests {
    @Test fun testExampleInput1() {
        assertEquals(" █\n█ ", day08b("0222112222120000", 2, 2))
    }

    @Test fun testActualInput() {
        assertEquals("" +
                "  ██  ██  ███   ██  ███  \n" +
                "   █ █  █ █  █ █  █ █  █ \n" +
                "   █ █    █  █ █    ███  \n" +
                "   █ █    ███  █    █  █ \n" +
                "█  █ █  █ █ █  █  █ █  █ \n" +
                " ██   ██  █  █  ██  ███  ",
            day08b(readInputLine("day08", 2019)))
    }
}
