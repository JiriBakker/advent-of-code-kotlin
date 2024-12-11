import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day11aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(22, day11a(listOf(
            "125 17"), blinks = 6))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(55312, day11a(listOf(
            "125 17")))
    }

    @Test
    fun testActualInput() {
        assertEquals(186203, day11a(readInputLines("day11")))
    }
}

class Day11bTests {
    @Test
    fun testActualInput() {
        assertEquals(221291560078593, day11b(readInputLines("day11")))
    }
}
