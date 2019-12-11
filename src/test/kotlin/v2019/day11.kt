package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day11.day11a
import v2019.days.day11.day11b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day11aTests {
    @Test fun testActualInput() {
        assertEquals(2883, day11a(readInputLine("day11")))
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
            "████ ████ █     ██  █    ████  ███ ████\n",
            day11b(readInputLine("day11")))
    }
}