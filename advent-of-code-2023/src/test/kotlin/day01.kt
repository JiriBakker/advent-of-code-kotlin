import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day01aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(142, day01a(listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet")))
    }

    @Test
    fun testActualInput() {
        assertEquals(54990, day01a(readInputLines("day01")))
    }
}

class Day01bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(281, day01b(listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen")))
    }

    @Test
    fun testActualInput() {
        assertEquals(54473, day01b(readInputLines("day01")))
    }

}
