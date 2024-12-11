import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day10aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1, day10a(listOf(
            "0123",
            "1234",
            "8765",
            "9876")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(2, day10a(listOf(
            "...0...",
            "...1...",
            "...2...",
            "6543456",
            "7.....7",
            "8.....8",
            "9.....9")))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(4, day10a(listOf(
            "..90..9",
            "...1.98",
            "...2..7",
            "6543456",
            "765.987",
            "876....",
            "987....")))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(3, day10a(listOf(
            "10..9..",
            "2...8..",
            "3...7..",
            "4567654",
            "...8..3",
            "...9..2",
            ".....01")))
    }

    @Test
    fun testExampleInput5() {
        assertEquals(36, day10a(listOf(
            "89010123",
            "78121874",
            "87430965",
            "96549874",
            "45678903",
            "32019012",
            "01329801",
            "10456732")))
    }

    @Test
    fun testActualInput() {
        assertEquals(514, day10a(readInputLines("day10")))
    }
}

class Day10bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3, day10b(listOf(
            ".....0.",
            "..4321.",
            "..5..2.",
            "..6543.",
            "..7..4.",
            "..8765.",
            "..9....")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(13, day10b(listOf(
            "..90..9",
            "...1.98",
            "...2..7",
            "6543456",
            "765.987",
            "876....",
            "987....")))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(227, day10b(listOf(
            "012345",
            "123456",
            "234567",
            "345678",
            "4.6789",
            "56789.")))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(81, day10b(listOf(
            "89010123",
            "78121874",
            "87430965",
            "96549874",
            "45678903",
            "32019012",
            "01329801",
            "10456732")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1162, day10b(readInputLines("day10")))
    }
}
