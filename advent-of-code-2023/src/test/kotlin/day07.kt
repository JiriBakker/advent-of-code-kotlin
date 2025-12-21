import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day07aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(6440, day07a(listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483")))
    }

    @Test
    fun testActualInput() {
        assertEquals(251121738, day07a(readInputLines("day07")))
    }
}

class Day07bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(5905, day07b(listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483")))
    }

    @Test
    fun testActualInput() {
        assertEquals(251421071, day07b(readInputLines("day07")))
    }

}
