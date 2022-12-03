import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1588, day14a(listOf(
            "NNCB",
            "",
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(2988, day14a(readInputLines("day14", 2021)))
    }
}

class Day14bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2188189693529, day14b(listOf(
            "NNCB",
            "",
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(3572761917024, day14b(readInputLines("day14", 2021)))
    }
}
