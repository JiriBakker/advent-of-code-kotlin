import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(126384, day21a(listOf(
            "029A",
            "980A",
            "179A",
            "456A",
            "379A")))
    }

    @Test
    fun testCustomInput1() {
        assertEquals(1972, day21a(listOf(
            "029A")))
    }

    @Test
    fun testCustomInput2() {
        assertEquals(45560, day21a(listOf(
            "670A")))
    }

    @Test
    fun testActualInput() {
        assertEquals(202274, day21a(readInputLines("day21")))
    }
}

class Day21bTests {
    @Test
    fun testCustomInput1() {
        assertEquals(26173969686128, day21b(listOf(
            "319A")))
    }

    @Test
    fun testCustomInput2() {
        assertEquals(56446219860480, day21b(listOf(
            "670A")))
    }
    @Test
    fun testCustomInput3() {
        assertEquals(30639988720644, day21b(listOf(
            "349A")))
    }
    @Test
    fun testCustomInput4() {
        assertEquals(81946718731032, day21b(listOf(
            "964A")))
    }
    @Test
    fun testCustomInput5() {
        assertEquals(50674808842688, day21b(listOf(
            "586A")))
    }

    @Test
    fun testActualInput() {
        assertEquals(245881705840972, day21b(readInputLines("day21")))
    }
}
