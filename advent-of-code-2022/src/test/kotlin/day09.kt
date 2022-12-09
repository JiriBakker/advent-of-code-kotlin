import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day09aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(13, day09a(listOf(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(6067, day09a(readInputLines("day09")))
    }
}

class Day09bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1, day09b(listOf(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(36, day09b(listOf(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20")))
    }

    @Test
    fun testActualInput() {
        assertEquals(2471, day09b(readInputLines("day09")))
    }
}
