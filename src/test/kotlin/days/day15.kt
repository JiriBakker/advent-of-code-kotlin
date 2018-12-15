package days.day15

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

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
            day15a(readInputLines("day15"))
        )
    }
}