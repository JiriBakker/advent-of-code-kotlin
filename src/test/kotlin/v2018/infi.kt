package v2018.extra.infi

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

class InfiATests {
    @Test fun testExampleInput() {
        assertEquals(6, infiA(listOf("╔═╗║", "╠╗╠║", "╬╬╣╬", "╚╩╩═")))
    }

    @Test fun testActualInput() {
        assertEquals(40, infiA(readInputLines("infi")))
    }
}

class InfiBTests {
    @Test fun testExampleInput() {
        assertEquals(4, infiB(listOf("╔═╗║", "╠╗╠║", "╬╬╣╬", "╚╩╩═")))
    }

    @Test fun testActualInput() {
        assertEquals(50, infiB(readInputLines("infi")))
    }
}
