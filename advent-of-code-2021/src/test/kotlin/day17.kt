import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day17aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(45, day17a(listOf(
            "target area: x=20..30, y=-10..-5"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(9180, day17a(readInputLines("day17")))
    }
}

class Day17bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(112, day17b(listOf(
            "target area: x=20..30, y=-10..-5"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(3767, day17b(readInputLines("day17")))
    }
}
