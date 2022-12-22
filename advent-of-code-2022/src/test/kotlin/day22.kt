import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day22aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(6032, day22a(listOf(
            "        ...#",
            "        .#..",
            "        #...",
            "        ....",
            "...#.......#",
            "........#...",
            "..#....#....",
            "..........#.",
            "        ...#....",
            "        .....#..",
            "        .#......",
            "        ......#.",
            "",
            "10R5L5R10L4R5L5")))
    }

    @Test
    fun testActualInput() {
        assertEquals(65368, day22a(readInputLines("day22")))
    }
}

class Day22bTests {
    @Test
    fun testActualInput() {
        assertEquals(156166, day22b(readInputLines("day22")))
    }
}
