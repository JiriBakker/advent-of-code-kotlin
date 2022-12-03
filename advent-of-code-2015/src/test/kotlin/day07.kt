import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals(65079, day07a(listOf(
            "123 -> x",
            "456 -> y",
            "x AND y -> d",
            "x OR y -> e",
            "x LSHIFT 2 -> f",
            "y RSHIFT 2 -> g",
            "NOT x -> h",
            "NOT y -> i"), "i"))
    }

    @Test fun testActualInput() {
        assertEquals(46065, day07a(readInputLines("day07")))
    }
}

class Day07bTests {
    @Test fun testActualInput() {
        assertEquals(14134, day07b(readInputLines("day07")))
    }
}

