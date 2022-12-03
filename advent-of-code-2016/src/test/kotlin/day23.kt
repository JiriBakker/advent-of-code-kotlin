import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day23a(listOf(
            "cpy 2 a",
            "tgl a",
            "tgl a",
            "tgl a",
            "cpy 1 a",
            "dec a",
            "dec a"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(13685, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(479010245, day23b(readInputLines("day23")))
    }
}
