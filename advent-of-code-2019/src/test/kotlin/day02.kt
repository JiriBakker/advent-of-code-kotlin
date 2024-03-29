import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(3500, day02a("1,9,10,3,2,3,11,0,99,30,40,50"))
    }

    @Test fun testExampleInput2() {
        assertEquals(2, day02a("1,0,0,0,99"))
    }

    @Test fun testExampleInput3() {
        assertEquals(2, day02a("2,3,0,3,99"))
    }

    @Test fun testExampleInput4() {
        assertEquals(2, day02a("2,4,4,5,99,0"))
    }

    @Test fun testExampleInput5() {
        assertEquals(30, day02a("1,1,1,4,99,5,6,0,99"))
    }

    @Test fun testActualInput() {
        assertEquals(3306701, day02a(readInputLine("day02"), listOf(Pair(1L, 12L), Pair(2L, 2L))))
    }
}

class Day02bTests {
    @Test fun testActualInput() {
        assertEquals(7621, day02b(readInputLine("day02")))
    }
}
