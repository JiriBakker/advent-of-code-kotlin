import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day10aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(7, day10a(listOf(
            "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
            "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
            "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}")))
    }

    @Test
    fun testActualInput() {
        assertEquals(422, day10a(readInputLines("day10")))
    }
}

class Day10bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(33, day10b(listOf(
            "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
            "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
            "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}")))
    }

    @Test
    fun testActualInput() {
        assertEquals(16361, day10b(readInputLines("day10")))
    }

}
