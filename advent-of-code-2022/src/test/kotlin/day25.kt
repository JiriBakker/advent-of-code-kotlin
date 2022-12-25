import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test
    fun testExampleInput1() {
        assertEquals("2=-1=0", day25a(listOf(
            "1=-0-2",
            "12111",
            "2=0=",
            "21",
            "2=01",
            "111",
            "20012",
            "112",
            "1=-1=",
            "1-12",
            "12",
            "1=",
            "122")))
    }

    @Test
    fun testSnafus() {
        listOf(
            1L  to     "1",
            2L  to     "2",
            3L  to    "1=",
            4L  to    "1-",
            5L  to    "10",
            6L  to    "11",
            7L  to    "12",
            8L  to    "2=",
            9L  to    "2-",
            10L to    "20",
            11L to    "21",
            12L to    "22",
            13L to   "1==",
            14L to   "1=-",
            15L to   "1=0",
            20L to   "1-0",
            38L to   "2==",
            43L to   "2-=",
            48L to   "20=",
            53L to   "21=",
            58L to   "22=",
            62L to   "222",
            63L to  "1===", // 125 + (-2 * 25) + (-2 * 5) + -2 = 125 - 50 - 10 - 2 = 63
            188L to "2===", // 250 + (-2 * 25) + (-2 * 5) + -2 = 125 - 50 - 10 - 2 = 188
            2022L to "1=11-2",
            12345L to "1-0---0",
            314159265L to "1121-1110-1=0",
            1747L to "1=-0-2",
            906L to "12111",
            198L to "2=0=",
            11L to "21",
            201L to "2=01",
            31L to "111",
            1257L to "20012",
            32L to "112",
            353L to "1=-1=",
            107L to "1-12",
            7L to "12",
            3L to "1=",
            37L to "122"
        ).forEach { (dec, snafu) ->
            assertEquals(snafu, dec.toSnafu())
        }
    }

    @Test
    fun testActualInput() {
        assertEquals("2-121-=10=200==2==21", day25a(readInputLines("day25")))
    }
}
