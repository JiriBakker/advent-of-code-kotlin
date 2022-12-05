import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines
import util.trimInput

class Day05aTests {
    @Test
    fun testExampleInput1() {
        assertEquals("CMZ", day05a(listOf(
            "    [D]    ",
            "[N] [C]    ",
            "[Z] [M] [P]",
            " 1   2   3 ",
            "",
            "move 1 from 2 to 1",
            "move 3 from 1 to 3",
            "move 2 from 2 to 1",
            "move 1 from 1 to 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals("VCTFTJQCG", day05a(readInputLines("day05")))
    }
}

class Day05bTests {
    @Test
    fun testExampleInput1() {
        assertEquals("MCD", day05b(listOf(
            "    [D]    ",
            "[N] [C]    ",
            "[Z] [M] [P]",
            " 1   2   3 ",
            "",
            "move 1 from 2 to 1",
            "move 3 from 1 to 3",
            "move 2 from 2 to 1",
            "move 1 from 1 to 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals("GCFGLDNJZ", day05b(readInputLines("day05")))
    }
}
