import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test fun testExampleInput1() {
        assertEquals(27730,
            day15a(
                listOf(
                    "#######",
                    "#.G...#",
                    "#...EG#",
                    "#.#.#G#",
                    "#..G#E#",
                    "#.....#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput2() {
        assertEquals(36334,
            day15a(
                listOf(
                    "#######",
                    "#G..#E#",
                    "#E#E.E#",
                    "#G.##.#",
                    "#...#E#",
                    "#...E.#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput3() {
        assertEquals(39514,
            day15a(
                listOf(
                    "#######",
                    "#E..EG#",
                    "#.#G.E#",
                    "#E.##E#",
                    "#G..#.#",
                    "#..E#.#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput4() {
        assertEquals(27755,
            day15a(
                listOf(
                    "#######",
                    "#E.G#.#",
                    "#.#G..#",
                    "#G.#.G#",
                    "#G..#.#",
                    "#...E.#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput5() {
        assertEquals(28944,
            day15a(
                listOf(
                    "#######",
                    "#.E...#",
                    "#.#..G#",
                    "#.###.#",
                    "#E#G#G#",
                    "#...#G#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput6() {
        assertEquals(18740,
            day15a(
                listOf(
                    "#########",
                    "#G......#",
                    "#.E.#...#",
                    "#..##..G#",
                    "#...##..#",
                    "#...#...#",
                    "#.G...G.#",
                    "#.....G.#",
                    "#########"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(197025,
            day15a(readInputLines("day15", 2018))
        )
    }
}

class Day15bTests {
    @Test fun testExampleInput1() {
        assertEquals(4988,
            day15b(
                listOf(
                    "#######",
                    "#.G...#",
                    "#...EG#",
                    "#.#.#G#",
                    "#..G#E#",
                    "#.....#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput3() {
        assertEquals(31284,
            day15b(
                listOf(
                    "#######",
                    "#E..EG#",
                    "#.#G.E#",
                    "#E.##E#",
                    "#G..#.#",
                    "#..E#.#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput4() {
        assertEquals(3478,
            day15b(
                listOf(
                    "#######",
                    "#E.G#.#",
                    "#.#G..#",
                    "#G.#.G#",
                    "#G..#.#",
                    "#...E.#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput5() {
        assertEquals(6474,
            day15b(
                listOf(
                    "#######",
                    "#.E...#",
                    "#.#..G#",
                    "#.###.#",
                    "#E#G#G#",
                    "#...#G#",
                    "#######"
                )
            )
        )
    }

    @Test fun testExampleInput6() {
        assertEquals(1140,
            day15b(
                listOf(
                    "#########",
                    "#G......#",
                    "#.E.#...#",
                    "#..##..G#",
                    "#...##..#",
                    "#...#...#",
                    "#.G...G.#",
                    "#.....G.#",
                    "#########"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(44423,
            day15b(readInputLines("day15", 2018))
        )
    }
}