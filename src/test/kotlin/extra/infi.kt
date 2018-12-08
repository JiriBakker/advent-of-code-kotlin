package extra

import org.junit.Assert.assertEquals
import org.junit.Test

class InfiATests {
    @Test fun testExampleInput() {
        assertEquals(6, infiA(listOf("╔═╗║", "╠╗╠║", "╬╬╣╬", "╚╩╩═")))
    }
}

class InfiBTests {
    @Test fun testExampleInput() {
        assertEquals(4, infiB(listOf("╔═╗║", "╠╗╠║", "╬╬╣╬", "╚╩╩═")))
    }
}
