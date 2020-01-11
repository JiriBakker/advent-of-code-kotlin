package v2017.days.day19

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals("ABCDEF", day19a(listOf(
            "     |          ",
            "     |  +--+    ",
            "     A  |  C    ",
            " F---|----E|--+ ",
            "     |  |  |  D ",
            "     +B-+  +--+ "
        )))
    }

    @Test fun testActualInput() {
        assertEquals("ITSZCJNMUO", day19a(readInputLines("day19", 2017)))
    }
}

class Day19bTests {
     @Test fun testExampleInput1() {
        assertEquals(38, day19b(listOf(
            "     |          ",
            "     |  +--+    ",
            "     A  |  C    ",
            " F---|----E|--+ ",
            "     |  |  |  D ",
            "     +B-+  +--+ "
        )))
    }

    @Test fun testActualInput() {
        assertEquals(17420, day19b(readInputLines("day19", 2017)))
    }
}
