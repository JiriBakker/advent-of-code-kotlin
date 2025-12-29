import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day24aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2, day24a(listOf(
            "19, 13, 30 @ -2,  1, -2",
            "18, 19, 22 @ -1, -1, -2",
            "20, 25, 34 @ -2, -2, -4",
            "12, 31, 28 @ -1, -2, -1",
            "20, 19, 15 @  1, -5, -3"),
            allowedRange = 7.0 .. 27.0))
    }

    @Test
    fun testActualInput() {
        assertEquals(739960225, day24a(readInputLines("day24"), allowedRange = 200000000000000.0 .. 400000000000000.0))
    }
}

class Day24bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(47, day24b(listOf(
            "19, 13, 30 @ -2,  1, -2",
            "18, 19, 22 @ -1, -1, -2",
            "20, 25, 34 @ -2, -2, -4",
            "12, 31, 28 @ -1, -2, -1",
            "20, 19, 15 @  1, -5, -3")))
    }

    @Test
    fun testActualInput() {
        assertEquals(549873212220117, day24b(readInputLines("day24")))
    }
}
