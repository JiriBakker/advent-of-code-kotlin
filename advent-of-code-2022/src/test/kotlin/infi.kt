import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class InfiATests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, infiA(listOf(
            "draai 90",
            "loop 6",
            "spring 2",
            "draai -45",
            "loop 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, infiA(readInputLines("day04")))
    }
}

class InfiBTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, infiB(listOf(
            "draai 90",
            "loop 6",
            "spring 2",
            "draai -45",
            "loop 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day04b(readInputLines("day04")))
    }
}
