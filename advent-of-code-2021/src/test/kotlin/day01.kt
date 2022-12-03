import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day01aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(7, day01a(listOf(
            "199",
            "200",
            "208",
            "210",
            "200",
            "207",
            "240",
            "269",
            "260",
            "263")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1722, day01a(readInputLines("day01")))
    }
}

class Day01bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(5, day01b(listOf("199",
            "200",
            "208",
            "210",
            "200",
            "207",
            "240",
            "269",
            "260",
            "263")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1748, day01b(readInputLines("day01")))
    }
}
