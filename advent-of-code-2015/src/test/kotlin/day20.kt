import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLine

class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day20a("10"))
    }

    @Test fun testExampleInput2() {
        assertEquals(2, day20a("30"))
    }

    @Test fun testExampleInput3() {
        assertEquals(3, day20a("40"))
    }

    @Test fun testExampleInput4() {
        assertEquals(4, day20a("70"))
    }

    @Test fun testExampleInput5() {
        assertEquals(6, day20a("120"))
    }

    @Test fun testExampleInput6() {
        assertEquals(8, day20a("150"))
    }

    @Disabled // Too slow
    @Test fun testActualInput() {
        assertEquals(665280, day20a(readInputLine("day20")))
    }
}

class Day20bTests {
    @Disabled // Too slow
    @Test fun testActualInput() {
        assertEquals(705600, day20b(readInputLine("day20")))
    }
}