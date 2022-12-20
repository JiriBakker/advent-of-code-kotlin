import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day20aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3, day20a(listOf(
            "1",
            "2",
            "-3",
            "3",
            "-2",
            "0",
            "4")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(0, day20a(listOf(
            "1",
            "2",
            "-3",
            "3",
            "-9",
            "0",
            "4")))
    }

    @Test
    fun testCustomInput2() {
        assertEquals(4, day20a(listOf(
            "6",
            "2",
            "-3",
            "3",
            "-2",
            "0",
            "4")))
    }

    @Test
    fun testCustomInput3() {
        assertEquals(-5, day20a(listOf(
            "1",
            "2",
            "-3",
            "3",
            "-6",
            "0",
            "4")))
    }

    @Test
    fun testActualInput() {
        assertEquals(13883, day20a(readInputLines("day20")))
    }
}

class Day20bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1623178306L, day20b(listOf(
            "1",
            "2",
            "-3",
            "3",
            "-2",
            "0",
            "4")))
    }

    @Test
    fun testActualInput() {
        assertEquals(19185967576920L, day20b(readInputLines("day20")))
    }
}
