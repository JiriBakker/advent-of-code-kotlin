import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day02aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1227775554, day02a(
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124"))
    }

    @Test
    fun testActualInput() {
        assertEquals(35367539282, day02a(readInputLine("day02")))
    }
}

class Day02bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4174379265, day02b(
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                    "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                    "824824821-824824827,2121212118-2121212124"))
    }

    @Test
    fun testActualInput() {
        assertEquals(45814076230, day02b(readInputLine("day02")))
    }

}
