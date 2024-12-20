import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day20aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(44, day20a(listOf(
            "###############",
            "#...#...#.....#",
            "#.#.#.#.#.###.#",
            "#S#...#.#.#...#",
            "#######.#.#.###",
            "#######.#.#...#",
            "#######.#.###.#",
            "###..E#...#...#",
            "###.#######.###",
            "#...###...#...#",
            "#.#####.#.###.#",
            "#.#...#.#.#...#",
            "#.#.#.#.#.#.###",
            "#...#...#...###",
            "###############"), minSave=2))
    }

    @Test
    fun testActualInput() {
        assertEquals(1452, day20a(readInputLines("day20")))
    }
}

class Day20bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(285, day20b(listOf(
            "###############",
            "#...#...#.....#",
            "#.#.#.#.#.###.#",
            "#S#...#.#.#...#",
            "#######.#.#.###",
            "#######.#.#...#",
            "#######.#.###.#",
            "###..E#...#...#",
            "###.#######.###",
            "#...###...#...#",
            "#.#####.#.###.#",
            "#.#...#.#.#...#",
            "#.#.#.#.#.#.###",
            "#...#...#...###",
            "###############"), minSave=50))
    }

    @Test
    fun testActualInput() {
        assertEquals(999556, day20b(readInputLines("day20")))
    }
}
