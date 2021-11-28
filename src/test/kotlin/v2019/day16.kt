package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day16aTests {
    @Test fun testExampleInput1() {
        assertEquals("01029498", day16a("12345678", 4))
    }

    @Test fun testExampleInput2() {
        assertEquals("24176176", day16a("80871224585914546619083218645595"))
    }

    @Test fun testExampleInput3() {
        assertEquals("73745418", day16a("19617804207202209144916044189917"))
    }

    @Test fun testExampleInput4() {
        assertEquals("52432133", day16a("69317163492948606335995924319873"))
    }

    @Test fun testActualInput() {
        assertEquals("74369033", day16a(readInputLine("day16", 2019)))
    }
}

class Day16bTests {
    @Test fun testExampleInput1() {
        assertEquals("84462026", day16b("03036732577212944063491565474664"))
    }

    @Test fun testExampleInput2() {
        assertEquals("78725270", day16b("02935109699940807407585447034323"))
    }

    @Test fun testExampleInput3() {
        assertEquals("53553731", day16b("03081770884921959731165446850517"))
    }

    @Test fun testActualInput() {
        assertEquals("19903864", day16b(readInputLine("day16", 2019)))
    }
}
