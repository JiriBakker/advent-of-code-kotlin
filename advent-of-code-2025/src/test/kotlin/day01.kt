import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day01aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3, day01a(listOf(
            "L68",
            "L30",
            "R48",
            "L5",
            "R60",
            "L55",
            "L1",
            "L99",
            "R14",
            "L82")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1086, day01a(readInputLines("day01")))
    }
}

class Day01bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(6, day01b(listOf(
            "L68",
            "L30",
            "R48",
            "L5",
            "R60",
            "L55",
            "L1",
            "L99",
            "R14",
            "L82")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(10, day01b(listOf(
            "R1000")))
    }

    @Test
    fun testCustomInput2() {
        assertEquals(10, day01b(listOf(
            "L1000")))
    }

    @Test
    fun testCustomInput3() {
        assertEquals(3, day01b(listOf(
            "R150",
            "R100")))
    }

    @Test
    fun testCustomInput4() {
        assertEquals(2, day01b(listOf(
            "L150")))
    }

    @Test
    fun testActualInput() {
        assertEquals(6268, day01b(readInputLines("day01")))
    }

}
