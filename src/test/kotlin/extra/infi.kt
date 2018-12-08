package extra

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

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
