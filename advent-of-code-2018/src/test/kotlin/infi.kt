import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

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
