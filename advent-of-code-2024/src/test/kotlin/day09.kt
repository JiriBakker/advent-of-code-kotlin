import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day09aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(105, day09a(listOf(
            "12345")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(1928, day09a(listOf(
            "2333133121414131402")))
    }

    @Test
    fun testActualInput() {
        assertEquals(6337921897505, day09a(readInputLines("day09")))
    }
}

class Day09bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2858, day09b(listOf(
            "2333133121414131402")))
    }

    @Test
    fun testActualInput() {
        assertEquals(6362722604045, day09b(readInputLines("day09")))
    }
}
