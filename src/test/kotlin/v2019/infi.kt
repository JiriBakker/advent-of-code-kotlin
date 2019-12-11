package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.extra.infiA
import v2019.extra.infiB
import v2019.util.readInputLines

class InfiATests {
    @Test fun testExampleInput1() {
        assertEquals(4, infiA(
            listOf(
                "flats", "1,4", "3,8", "4,3", "5,7", "7,4", "10,3",
                "sprongen", "2,0", "0,4", "1,0", "0,0"
            )))
    }

    @Test fun testActualInput() {
        assertEquals(7, infiA(readInputLines("infi")))
    }
}

class InfiBTests {
    @Test fun testActualInput() {
        assertEquals(58, infiB(readInputLines("infi")))
    }
}
