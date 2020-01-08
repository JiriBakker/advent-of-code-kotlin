package v2017.days.day07

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals("tknk", day07a(listOf(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (68) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)")))
    }

    @Test fun testActualInput() {
        assertEquals("eqgvf", day07a(readInputLines("day07", 2017)))
    }
}

class Day07bTests {
    @Test fun testExampleInput1() {
        assertEquals(60, day07b(listOf(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (68) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)")))
    }

    @Test fun testActualInput() {
        assertEquals(757, day07b(readInputLines("day07", 2017)))
    }
}
