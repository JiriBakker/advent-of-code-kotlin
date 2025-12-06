import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day06aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4277556, day06a(listOf(
            "123 328  51 64 ",
            " 45 64  387 23 ",
            "  6 98  215 314",
            "*   +   *   +  ")))
    }

    @Test
    fun testActualInput() {
        assertEquals(4580995422905, day06a(readInputLines("day06")))
    }
}

class Day06bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3263827, day06b(listOf(
            "123 328  51 64 ",
            " 45 64  387 23 ",
            "  6 98  215 314",
            "*   +   *   +  ")))
    }

    @Test
    fun testActualInput() {
        assertEquals(10875057285868, day06b(readInputLines("day06")))
    }

}
