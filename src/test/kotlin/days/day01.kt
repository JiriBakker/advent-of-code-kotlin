package days

import org.junit.Assert.assertEquals
import org.junit.Test


class Day01aTests {
    private fun exampleInputToArray(exampleInput: String): List<String> {
        return exampleInput.split(", ")
    }

    @Test fun testExampleInput1() {
        assertEquals(3, day01a(exampleInputToArray("+1, -2, +3, +1")))
    }

    @Test fun testExampleInput2() {
        assertEquals(3, day01a(exampleInputToArray("+1, +1, +1")))
    }

    @Test fun testExampleInput3() {
        assertEquals(0, day01a(exampleInputToArray("+1, +1, -2")))
    }

    @Test fun testExampleInput4() {
        assertEquals(-6, day01a(exampleInputToArray("-1, -2, -3")))
    }
}

class Day01bTests {
    private fun exampleInputToArray(exampleInput: String): List<String> {
        return exampleInput.split(", ")
    }

    @Test fun testExampleInput1() {
        assertEquals(0, day01b(exampleInputToArray("+1, -1")))
    }

    @Test fun testExampleInput2() {
        assertEquals(10, day01b(exampleInputToArray("+3, +3, +4, -2, -4")))
    }

    @Test fun testExampleInput3() {
        assertEquals(5, day01b(exampleInputToArray("-6, +3, +8, +5, -6")))
    }

    @Test fun testExampleInput4() {
        assertEquals(14, day01b(exampleInputToArray("+7, +7, -2, -7, -4")))
    }
}

