package v2018.days.day02

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(12,
            day02a(listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab"))
        )
    }

    @Test fun testActualInput() {
        assertEquals(6888, day02a(readInputLines("day02")))
    }
}

class Day02bTests {
    @Test fun testExampleInput1() {
        assertEquals("fgij", day02b(listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")))
    }

    @Test fun testActualInput() {
        assertEquals("icxjvbrobtunlelzpdmfkahgs", day02b(readInputLines("day02")))
    }
}