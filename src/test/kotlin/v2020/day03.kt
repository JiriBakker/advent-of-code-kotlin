package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test fun testExampleInput1() {
        assertEquals(7, day03a(listOf(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#")))
    }

    @Test fun testActualInput() {
        assertEquals(294, day03a(readInputLines("day03", 2020)))
    }
}

class Day03bTests {
    @Test fun testExampleInput1() {
        assertEquals(336, day03b(listOf(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#")))
    }

    @Test fun testActualInput() {
        assertEquals(5774564250L, day03b(readInputLines("day03", 2020)))
    }
}
