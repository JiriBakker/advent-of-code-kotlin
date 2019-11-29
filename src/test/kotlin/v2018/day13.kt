package v2018.days.day13

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

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
            day13a(readInputLines("day13"))
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
            day13b(readInputLines("day13"))
        )
    }
}
