import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day08aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2, day08a(listOf(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(6, day08a(listOf(
            "LLR",
            "",
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)")))
    }

    @Test
    fun testActualInput() {
        assertEquals(17263, day08a(readInputLines("day08")))
    }
}

class Day08bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(6, day08b(listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)")))
    }

    @Test
    fun testActualInput() {
        assertEquals(14631604759649, day08b(readInputLines("day08")))
    }

}
