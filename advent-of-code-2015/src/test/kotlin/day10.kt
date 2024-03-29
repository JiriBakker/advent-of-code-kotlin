import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day10aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day10a("211", 1))
    }

    @Test fun testExampleInput2() {
        assertEquals(2, day10a("1", 1))
    }

    @Test fun testExampleInput3() {
        assertEquals(2, day10a("11", 1))
    }

    @Test fun testExampleInput4() {
        assertEquals(4, day10a("21", 1))
    }

    @Test fun testExampleInput5() {
        assertEquals(6, day10a("1211", 1))
    }

    @Test fun testExampleInput6() {
        assertEquals(6, day10a("111221", 1))
    }

    @Test fun testActualInput() {
        assertEquals(360154, day10a(readInputLine("day10")))
    }
}

class Day10bTests {
    @Test fun testActualInput() {
        assertEquals(5103798, day10b(readInputLine("day10")))
    }
}

