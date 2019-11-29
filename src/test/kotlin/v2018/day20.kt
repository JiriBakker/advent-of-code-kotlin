package v2018.days.day20

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLine

class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(3,
            day20a("^WNE\$")
        )
    }

    @Test fun testExampleInput2() {
        assertEquals(10,
            day20a("^ENWWW(NEEE|SSE(EE|N))\$")
        )
    }

    @Test fun testExampleInput3() {
        assertEquals(18,
            day20a("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN\$")
        )
    }

    @Test fun testExampleInput4() {
        assertEquals(23,
            day20a("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$")
        )
    }

    @Test fun testExampleInput5() {
        assertEquals(31,
            day20a("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$")
        )
    }

    @Test fun testActualInput() {
        assertEquals(4025,
            day20a(readInputLine("day20"))
        )
    }
}

class Day20bTests {
    @Test fun testActualInput() {
        assertEquals(8186,
            day20b(readInputLine("day20"))
        )
    }
}