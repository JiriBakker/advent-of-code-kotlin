package v2017.days.day01

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day01aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day01a("1122"))
    }

    @Test fun testExampleInput2() {
        assertEquals(4, day01a("1111"))
    }

    @Test fun testExampleInput3() {
        assertEquals(0, day01a("1234"))
    }

    @Test fun testExampleInput4() {
        assertEquals(9, day01a("91212129"))
    }

    @Test fun testActualInput() {
        assertEquals(1158, day01a(readInputLine("day01", 2017)))
    }
}

class Day01bTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day01b("1212"))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day01b("1221"))
    }

    @Test fun testExampleInput3() {
        assertEquals(4, day01b("123425"))
    }

    @Test fun testExampleInput4() {
        assertEquals(12, day01b("123123"))
    }

    @Test fun testExampleInput5() {
        assertEquals(4, day01b("12131415"))
    }

    @Test fun testActualInput() {
        assertEquals(1132, day01b(readInputLine("day01", 2017)))
    }
}
