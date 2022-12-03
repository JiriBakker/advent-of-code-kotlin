import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals(11, day06a(listOf(
            "abc",
            "",
            "a",
            "b",
            "c",
            "",
            "ab",
            "ac",
            "",
            "a",
            "a",
            "a",
            "a",
            "",
            "b")))
    }

    @Test fun testActualInput() {
        assertEquals(6612, day06a(readInputLines("day06", 2020)))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day06b(listOf(
            "abc",
            "",
            "a",
            "b",
            "c",
            "",
            "ab",
            "ac",
            "",
            "a",
            "a",
            "a",
            "a",
            "",
            "b")))
    }

    @Test fun testActualInput() {
        assertEquals(3268, day06b(readInputLines("day06", 2020)))
    }
}
