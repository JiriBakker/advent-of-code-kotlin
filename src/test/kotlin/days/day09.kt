package days.day09

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLine

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(32, day09a("9 players; last marble is worth 25 points"))
    }

    @Test fun testExampleInput2() {
        assertEquals(8317, day09a("10 players; last marble is worth 1618 points"))
    }

    @Test fun testExampleInput3() {
        assertEquals(146373, day09a("13 players; last marble is worth 7999 points"))
    }

    @Test fun testExampleInput4() {
        assertEquals(2764, day09a("17 players; last marble is worth 1104 points"))
    }

    @Test fun testExampleInput5() {
        assertEquals(54718, day09a("21 players; last marble is worth 6111 points"))
    }

    @Test fun testExampleInput6() {
        assertEquals(37305, day09a("30 players; last marble is worth 5807 points"))
    }

    @Test fun testCustomInput1() {
        assertEquals(63, day09a("9 players; last marble is worth 47 points"))
    }

    @Test fun testActualInput() {
        assertEquals(394486, day09a(readInputLine("day09")))
    }
}

class Day09bTests {
    @Test fun testActualInput() {
        assertEquals(3276488008, day09b(readInputLine("day09")))
    }
}