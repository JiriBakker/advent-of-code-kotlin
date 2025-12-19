import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4361, day03a(listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..")))
    }

    @Test
    fun testActualInput() {
        assertEquals(530495, day03a(readInputLines("day03")))
    }
}

class Day03bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(467835, day03b(listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..")))
    }

    @Test
    fun testActualInput() {
        assertEquals(80253814, day03b(readInputLines("day03")))
    }

}
