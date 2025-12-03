import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(357, day03a(listOf(
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111")))
    }

    @Test
    fun testActualInput() {
        assertEquals(17244, day03a(readInputLines("day03")))
    }
}

class Day03bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3121910778619, day03b(listOf(
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111")))
    }

    @Test
    fun testActualInput() {
        assertEquals(171435596092638, day03b(readInputLines("day03")))
    }

}
