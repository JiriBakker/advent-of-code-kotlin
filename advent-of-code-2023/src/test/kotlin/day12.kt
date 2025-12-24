import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {

    @Test
    fun testExampleInput1() {
        assertEquals(21, day12a(listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1")))
    }

    @Test
    fun testActualInput() {
        assertEquals(7286, day12a(readInputLines("day12")))
    }
}

class Day12bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(525152, day12b(listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1")))
    }

    @Test
    fun testActualInput() {
        assertEquals(25470469710341, day12b(readInputLines("day12")))
    }

}
