package v2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day14aTests {
    @Test fun testExampleInput1() {
        assertEquals("5158916779",
            day14a("9")
        )
    }

    @Test fun testExampleInput2() {
        assertEquals("0124515891",
            day14a("5")
        )
    }

    @Test fun testExampleInput3() {
        assertEquals("9251071085",
            day14a("18")
        )
    }

    @Test fun testExampleInput4() {
        assertEquals("5941429882",
            day14a("2018")
        )
    }

    @Test fun testActualInput() {
        assertEquals("1413131339",
            day14a(readInputLine("day14", 2018))
        )
    }
}

class Day14bTests {
    @Test fun testExampleInput1() {
        assertEquals(9,
            day14b("51589")
        )
    }

    @Test fun testExampleInput2() {
        assertEquals(5,
            day14b("01245")
        )
    }

    @Test fun testExampleInput3() {
        assertEquals(18,
            day14b("92510")
        )
    }

    @Test fun testExampleInput4() {
        assertEquals(2018,
            day14b("59414")
        )
    }

    @Test fun testActualInput() {
        assertEquals(
            20254833,
            day14b(readInputLine("day14", 2018))
        )
    }
}
