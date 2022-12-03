import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day13aTests {
    @Test fun testExampleInput1() {
        assertEquals(Pair(7, 3),
            day13a(
                listOf(
                    "/->-\\        ",
                    "|   |  /----\\",
                    "| /-+--+-\\  |",
                    "| | |  | v  |",
                    "\\-+-/  \\-+--/",
                    "  \\------/   "
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(Pair(111, 13),
            day13a(readInputLines("day13", 2018))
        )
    }
}

class Day13bTests {
    @Test fun testExampleInput1() {
        assertEquals(Pair(6, 4),
            day13b(
                listOf(
                    "/>-<\\  ",
                    "|   |  ",
                    "| /<+-\\",
                    "| | | v",
                    "\\>+</ |",
                    "  |   ^",
                    "  \\<->/"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(Pair(16, 73),
            day13b(readInputLines("day13", 2018))
        )
    }
}
