import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day07aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3749, day07a(listOf(
            "190: 10 19",
            "3267: 81 40 27",
            "83: 17 5",
            "156: 15 6",
            "7290: 6 8 6 15",
            "161011: 16 10 13",
            "192: 17 8 14",
            "21037: 9 7 18 13",
            "292: 11 6 16 20")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1298300076754, day07a(readInputLines("day07")))
    }
}

class Day07bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(11387, day07b(listOf(
            "190: 10 19",
            "3267: 81 40 27",
            "83: 17 5",
            "156: 15 6",
            "7290: 6 8 6 15",
            "161011: 16 10 13",
            "192: 17 8 14",
            "21037: 9 7 18 13",
            "292: 11 6 16 20")))
    }

    @Test
    fun testActualInput() {
        assertEquals(248427118972289, day07b(readInputLines("day07")))
    }
}
