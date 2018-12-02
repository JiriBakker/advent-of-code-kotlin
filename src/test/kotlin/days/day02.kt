package days

import org.junit.Assert.assertEquals
import org.junit.Test

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(12, day02a(listOf("abcdef","bababc","abbcde","abcccd","aabcdd","abcdee","ababab")))
    }
}

class Day02bTests {
    @Test fun testExampleInput1() {
        assertEquals("fgij", day02b(listOf("abcde","fghij","klmno","pqrst","fguij","axcye","wvxyz")))
    }
}