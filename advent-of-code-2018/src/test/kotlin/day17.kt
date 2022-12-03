import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day17aTests {
    @Test fun testExampleInput1() {
        assertEquals(57,
            day17a(
                listOf(
                    "x=495, y=2..7",
                    "y=7, x=495..501",
                    "x=501, y=3..7",
                    "x=498, y=2..4",
                    "x=506, y=1..2",
                    "x=498, y=10..13",
                    "x=504, y=10..13",
                    "y=13, x=498..504"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(31013,
            day17a(readInputLines("day17"))
        )
    }
}

class Day17bTests {
    @Test fun testExampleInput1() {
        assertEquals(29,
            day17b(
                listOf(
                    "x=495, y=2..7",
                    "y=7, x=495..501",
                    "x=501, y=3..7",
                    "x=498, y=2..4",
                    "x=506, y=1..2",
                    "x=498, y=10..13",
                    "x=504, y=10..13",
                    "y=13, x=498..504"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(25448,
            day17b(readInputLines("day17"))
        )
    }
}