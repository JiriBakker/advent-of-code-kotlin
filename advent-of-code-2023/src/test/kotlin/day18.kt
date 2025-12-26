import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLines

class Day18aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(62, day18a(listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(58, day18a(listOf(
            "R 2 (#70c710)",
            "D 3 (#0dc571)",
            "R 5 (#5713f0)",
            "U 3 (#d2c081)",
            "R 2 (#59c680)",
            "D 6 (#411b91)",
            "L 9 (#8ceee2)",
            "U 6 (#caa173)")))
    }

    @Test
    fun testActualInput() {
        assertEquals(47045, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(952408144115L, day18b(listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(271979966008, day18b(listOf(
            "R 2 (#70c710)",
            "D 3 (#0dc571)",
            "R 5 (#5713f0)",
            "U 3 (#d2c081)",
            "R 2 (#59c680)",
            "D 6 (#411b91)",
            "L 9 (#8ceee2)",
            "U 6 (#caa173)")))
    }

    @Test
    @Disabled("Requires a lot of memory (~24GB) and time (~20 seconds)")
    fun testActualInputDay18b() {
        assertEquals(147839570293376, day18b(readInputLines("day18")))
    }
}
