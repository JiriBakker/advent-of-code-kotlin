package v2018

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class InfiATests {
    @Test fun testExampleInput() {
        assertEquals(6, infiA(listOf("╔═╗║", "╠╗╠║", "╬╬╣╬", "╚╩╩═")))
    }

    @Test fun testActualInput() {
        assertEquals(40, infiA(readInputLines("infi", 2018)))
    }
}

class InfiBTests {
    @Test fun testExampleInput() {
        assertEquals(4, infiB(listOf("╔═╗║", "╠╗╠║", "╬╬╣╬", "╚╩╩═")))
    }

    @Test fun testActualInput() {
        assertEquals(50, infiB(readInputLines("infi", 2018)))
    }
}
