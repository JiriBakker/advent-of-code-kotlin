import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(12521, day23a(listOf(
            "#############",
            "#...........#",
            "###B#C#B#D###",
            "  #A#D#C#A#",
            "  #########"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(17120, day23a(readInputLines("day23", 2021)))
    }
}

class Day23bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(44169, day23b(listOf(
            "#############",
            "#...........#",
            "###B#C#B#D###",
            "  #A#D#C#A#",
            "  #########"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(47234, day23b(readInputLines("day23", 2021)))
    }
}
