import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day22aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(37327623, day22a(listOf(
            "1",
            "10",
            "100",
            "2024")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(5908254, day22a(listOf(
            "123"), loops = 10))
    }

    @Test
    fun testActualInput() {
        assertEquals(19847565303, day22a(readInputLines("day22")))
    }
}

class Day22bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(23, day22b(listOf(
            "1",
            "2",
            "3",
            "2024")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(6, day22b(listOf(
            "123"), loops = 10))
    }

    @Test
    fun testActualInput() {
        assertEquals(2250, day22b(readInputLines("day22")))
    }
}
