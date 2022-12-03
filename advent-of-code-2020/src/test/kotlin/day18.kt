import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(71, day18a(listOf(
            "1 + 2 * 3 + 4 * 5 + 6")))
    }

    @Test fun testExampleInput2() {
        assertEquals(51, day18a(listOf(
            "1 + (2 * 3) + (4 * (5 + 6))")))
    }

    @Test fun testExampleInput3() {
        assertEquals(26, day18a(listOf(
            "2 * 3 + (4 * 5)")))
    }

    @Test fun testExampleInput4() {
        assertEquals(437, day18a(listOf(
            "5 + (8 * 3 + 9 + 3 * 4 * 3)")))
    }

    @Test fun testExampleInput5() {
        assertEquals(12240, day18a(listOf(
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")))
    }

    @Test fun testExampleInput6() {
        assertEquals(13632, day18a(listOf(
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")))
    }

    @Test fun testActualInput() {
        assertEquals(11004703763391L, day18a(readInputLines("day18", 2020)))
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(231, day18b(listOf(
            "1 + 2 * 3 + 4 * 5 + 6")))
    }

    @Test fun testExampleInput2() {
        assertEquals(51, day18b(listOf(
            "1 + (2 * 3) + (4 * (5 + 6))")))
    }

    @Test fun testExampleInput3() {
        assertEquals(46, day18b(listOf(
            "2 * 3 + (4 * 5)")))
    }

    @Test fun testExampleInput4() {
        assertEquals(1445, day18b(listOf(
            "5 + (8 * 3 + 9 + 3 * 4 * 3)")))
    }

    @Test fun testExampleInput5() {
        assertEquals(669060, day18b(listOf(
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")))
    }

    @Test fun testExampleInput6() {
        assertEquals(23340, day18b(listOf(
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")))
    }

    @Test fun testActualInput() {
        assertEquals(290726428573651L, day18b(readInputLines("day18", 2020)))
    }
}
