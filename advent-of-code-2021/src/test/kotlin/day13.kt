import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day13aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(17, day13a(listOf(
            "6,10",
            "0,14",
            "9,10",
            "0,3",
            "10,4",
            "4,11",
            "6,0",
            "6,12",
            "4,1",
            "0,13",
            "10,12",
            "3,4",
            "3,0",
            "8,4",
            "1,10",
            "2,14",
            "8,10",
            "9,0",
            "",
            "fold along y=7",
            "fold along x=5"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(655, day13a(readInputLines("day13", 2021)))
    }
}

class Day13bTests {
    @Test
    fun testActualInput() {
        assertEquals(
            listOf(
                "..##.###..####..##..#..#..##..#..#.###.",
                "...#.#..#....#.#..#.#..#.#..#.#..#.#..#",
                "...#.#..#...#..#....#..#.#..#.#..#.#..#",
                "...#.###...#...#....#..#.####.#..#.###.",
                "#..#.#....#....#..#.#..#.#..#.#..#.#.#.",
                ".##..#....####..##...##..#..#..##..#..#"
            ),
            day13b(readInputLines("day13", 2021)))
    }
}
