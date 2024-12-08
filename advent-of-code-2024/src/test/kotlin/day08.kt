import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day08aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(14, day08a(listOf(
            "............",
            "........0...",
            ".....0......",
            ".......0....",
            "....0.......",
            "......A.....",
            "............",
            "............",
            "........A...",
            ".........A..",
            "............",
            "............")))
    }

    @Test
    fun testActualInput() {
        assertEquals(341, day08a(readInputLines("day08")))
    }
}

class Day08bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(9, day08b(listOf(
            "T.........",
            "...T......",
            ".T........",
            "..........",
            "..........",
            "..........",
            "..........",
            "..........",
            "..........",
            "..........")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(34, day08b(listOf(
            "............",
            "........0...",
            ".....0......",
            ".......0....",
            "....0.......",
            "......A.....",
            "............",
            "............",
            "........A...",
            ".........A..",
            "............",
            "............")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1134, day08b(readInputLines("day08")))
    }
}
