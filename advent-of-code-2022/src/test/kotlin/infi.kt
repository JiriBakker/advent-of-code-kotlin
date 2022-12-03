import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class InfiATests {
    @Test
    fun testExampleInput1() {
        assertEquals(12, infiA(listOf(
            "draai 90",
            "loop 6",
            "spring 2",
            "draai -45",
            "loop 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(64, infiA(readInputLines("infi")))
    }
}

class InfiBTests {
    @Test
    fun testActualInput() {
        assertEquals(64, infiB(readInputLines("infi")))
    }
}
