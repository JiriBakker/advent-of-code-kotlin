import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(127, day09a(listOf(
            "35",
            "20",
            "15",
            "25",
            "47",
            "40",
            "62",
            "55",
            "65",
            "95",
            "102",
            "117",
            "150",
            "182",
            "127",
            "219",
            "299",
            "277",
            "309",
            "576"), 5))
    }

    @Test fun testActualInput() {
        assertEquals(248131121, day09a(readInputLines("day09", 2020)))
    }
}

class Day09bTests {
    @Test fun testExampleInput1() {
        assertEquals(62, day09b(listOf(
            "35",
            "20",
            "15",
            "25",
            "47",
            "40",
            "62",
            "55",
            "65",
            "95",
            "102",
            "117",
            "150",
            "182",
            "127",
            "219",
            "299",
            "277",
            "309",
            "576"), 5))
    }

    @Test fun testActualInput() {
        assertEquals(31580383, day09b(readInputLines("day09", 2020)))
    }
}
