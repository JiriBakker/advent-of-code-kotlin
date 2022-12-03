import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day20a(listOf(
            "5-8",
            "0-2",
            "4-7")))
    }

    @Test fun testActualInput() {
        assertEquals(32259706, day20a(readInputLines("day20", 2016)))
    }
}

class Day20bTests {
    @Test fun testExampleInput1() {
        assertEquals(2, day20b(listOf(
            "5-8",
            "0-2",
            "4-7"), 9))
    }

    @Test fun testCustomInput1() {
        assertEquals(0, day20b(listOf(
            "0-9",
            "10-19",
            "20-29"), 29))
    }

    @Test fun testCustomInput2() {
        assertEquals(1, day20b(listOf(
            "0-9",
            "10-19",
            "20-29"), 30))
    }

    @Test fun testCustomInput3() {
        assertEquals(10, day20b(listOf(
            "0-9",
            "20-29"), 29))
    }

    @Test fun testCustomInput4() {
        assertEquals(1, day20b(listOf(
            "0-9",
            "11-19",
            "20-29"), 29))
    }

    @Test fun testCustomInput5() {
        assertEquals(0, day20b(listOf(
            "0-29",
            "11-19",
            "20-28"), 29))
    }

    @Test fun testCustomInput6() {
        assertEquals(0, day20b(listOf(
            "0-19",
            "11-29"), 29))
    }

    @Test fun testActualInput() {
        assertEquals(113, day20b(readInputLines("day20", 2016)))
    }
}