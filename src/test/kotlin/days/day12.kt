package days.day12

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

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
            day12a(readInputLines("day12"))
        )
    }
}

class Day12bTests {
    @Test fun testActualInput() {
        assertEquals(2600000001872L,
            day12b(readInputLines("day12"))
        )
    }
}