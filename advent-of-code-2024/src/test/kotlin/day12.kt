import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(140, day12a(listOf(
            "AAAA",
            "BBCD",
            "BBCC",
            "EEEC")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(772, day12a(listOf(
            "OOOOO",
            "OXOXO",
            "OOOOO",
            "OXOXO",
            "OOOOO")))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(1930, day12a(listOf(
            "RRRRIICCFF",
            "RRRRIICCCF",
            "VVRRRCCFFF",
            "VVRCCCJFFF",
            "VVVVCJJCFE",
            "VVIVCCJJEE",
            "VVIIICJJEE",
            "MIIIIIJJEE",
            "MIIISIJEEE",
            "MMMISSJEEE")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1319878, day12a(readInputLines("day12")))
    }
}

class Day12bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(80, day12b(listOf(
            "AAAA",
            "BBCD",
            "BBCC",
            "EEEC")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(436, day12b(listOf(
            "OOOOO",
            "OXOXO",
            "OOOOO",
            "OXOXO",
            "OOOOO")))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(236, day12b(listOf(
            "EEEEE",
            "EXXXX",
            "EEEEE",
            "EXXXX",
            "EEEEE")))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(368, day12b(listOf(
            "AAAAAA",
            "AAABBA",
            "AAABBA",
            "ABBAAA",
            "ABBAAA",
            "AAAAAA")))
    }

    @Test
    fun testExampleInput5() {
        assertEquals(1206, day12b(listOf(
            "RRRRIICCFF",
            "RRRRIICCCF",
            "VVRRRCCFFF",
            "VVRCCCJFFF",
            "VVVVCJJCFE",
            "VVIVCCJJEE",
            "VVIIICJJEE",
            "MIIIIIJJEE",
            "MIIISIJEEE",
            "MMMISSJEEE")))
    }

    @Test
    fun testActualInput() {
        assertEquals(784982, day12b(readInputLines("day12")))
    }
}
