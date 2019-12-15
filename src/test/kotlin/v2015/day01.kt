package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day01.day01a
import v2015.days.day01.day01b
import v2015.util.readInputLine


class Day01aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day01a("(())"))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day01a("()()"))
    }

    @Test fun testExampleInput3() {
        assertEquals(3, day01a("((("))
    }

    @Test fun testExampleInput4() {
        assertEquals(3, day01a("(()(()("))
    }

    @Test fun testExampleInput5() {
        assertEquals(3, day01a("))((((("))
    }

    @Test fun testExampleInput6() {
        assertEquals(-1, day01a("())"))
    }

    @Test fun testExampleInput7() {
        assertEquals(-1, day01a("))("))
    }

    @Test fun testExampleInput8() {
        assertEquals(-3, day01a(")))"))
    }

    @Test fun testExampleInput9() {
        assertEquals(-3, day01a(")())())"))
    }

    @Test fun testActualInput() {
        assertEquals(74, day01a(readInputLine("day01")))
    }
}

class Day01bTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day01b(")"))
    }

    @Test fun testExampleInput2() {
        assertEquals(5, day01b("()())"))
    }

    @Test fun testActualInput() {
        assertEquals(1795, day01b(readInputLine("day01")))
    }
}
