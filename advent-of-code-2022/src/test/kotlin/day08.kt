import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day08aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(21, day08a(listOf(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1538, day08a(readInputLines("day08")))
    }
}

class Day08bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(8, day08b(listOf(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390")))
    }

    @Test
    fun testActualInput() {
        assertEquals(496125, day08b(readInputLines("day08")))
    }
}
