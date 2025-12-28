import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day20aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(32000000, day20a(listOf(
            "broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(11687500, day20a(listOf(
            "broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "&con -> output")))
    }

    @Test
    fun testActualInput() {
        assertEquals(739960225, day20a(readInputLines("day20")))
    }
}

class Day20bTests {
    @Test
    fun testActualInput() {
        assertEquals(231897990075517, day20b(readInputLines("day20")))
    }
}
