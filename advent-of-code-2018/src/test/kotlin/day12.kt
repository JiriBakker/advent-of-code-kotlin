import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(325,
            day12a(
                listOf(
                    "initial state: #..#.#..##......###...###",
                    "",
                    "...## => #",
                    "..#.. => #",
                    ".#... => #",
                    ".#.#. => #",
                    ".#.## => #",
                    ".##.. => #",
                    ".#### => #",
                    "#.#.# => #",
                    "#.### => #",
                    "##.#. => #",
                    "##.## => #",
                    "###.. => #",
                    "###.# => #",
                    "####. => #",
                    "..... => .",
                    "#..## => .",
                    "..### => .",
                    "..#.# => .",
                    "#.... => .",
                    "#...# => .",
                    "##..# => .",
                    ".###. => .",
                    "##### => .",
                    "#.#.. => .",
                    "...#. => .",
                    "#.##. => .",
                    "##... => .",
                    "....# => .",
                    "#..#. => .",
                    "..##. => .",
                    ".##.# => .",
                    ".#..# => ."
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(3221,
            day12a(readInputLines("day12", 2018))
        )
    }
}

class Day12bTests {
    @Test fun testActualInput() {
        assertEquals(2600000001872L,
            day12b(readInputLines("day12", 2018))
        )
    }
}