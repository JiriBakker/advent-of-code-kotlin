package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day11.day11a
import v2019.days.day11.day11b
import util.readInputLine

class Day11aTests {
    @Test fun testActualInput() {
        assertEquals(2883, day11a(readInputLine("day11", 2019)))
    }
}

class Day11bTests {
    @Test fun testActualInput() {
        assertEquals("" +
            "█    ████ ███   ██  ███  █     ██  ████\n" +
            "█    █    █  █ █  █ █  █ █    █  █    █\n" +
            "█    ███  █  █ █    █  █ █    █      █ \n" +
            "█    █    ███  █    ███  █    █ ██  █  \n" +
            "█    █    █    █  █ █    █    █  █ █   \n" +
            "████ ████ █     ██  █    ████  ███ ████",
            day11b(readInputLine("day11", 2019)))
    }
}