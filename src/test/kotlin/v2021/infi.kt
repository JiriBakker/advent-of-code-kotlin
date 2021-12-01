package v2021

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLines

class InfiATests {
    @Test
    fun testExampleInput1() {
        assertEquals(18, infiA(listOf(
            "35 onderdelen missen",
            "Zoink: 9 Oink, 5 Dink",
            "Floep: 2 Flap, 4 Dink",
            "Flap: 4 Oink, 3 Dink"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(128844, infiA(readInputLines("infi", 2021)))
    }
}

class InfiBTests {
    @Test
    fun testExampleInput1() {
        assertEquals("FZZ", infiB(listOf(
            "35 onderdelen missen",
            "Zoink: 9 Oink, 5 Dink",
            "Floep: 2 Flap, 4 Dink",
            "Flap: 4 Oink, 3 Dink"
        ), 3))
    }

    @Test
    @Ignore // (Apparently) not the correct answer :|
    fun testActualInput() {
        assertEquals("AAAAAABBBBBBDEEEEEPU", infiB(readInputLines("infi", 2021)))
    }
}