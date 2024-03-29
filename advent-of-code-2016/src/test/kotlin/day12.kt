import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(42, day12a(listOf(
            "cpy 41 a",
            "inc a",
            "inc a",
            "dec a",
            "jnz a 2",
            "dec a"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(318009, day12a(readInputLines("day12")))
    }
}

class Day12bTests {
    @Test fun testActualInput_highMemUsage() {
        assertEquals(9227663, day12b(readInputLines("day12")))
    }
}
