import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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