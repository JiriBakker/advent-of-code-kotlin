package v2016

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day07a(listOf("abba[mnop]qrst")))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day07a(listOf("abcd[bddb]xyyx")))
    }

    @Test fun testExampleInput3() {
        assertEquals(0, day07a(listOf("aaaa[qwer]tyui")))
    }

    @Test fun testExampleInput4() {
        assertEquals(1, day07a(listOf("ioxxoj[asdfgh]zxcvbn")))
    }

    @Test fun testActualInput() {
        assertEquals(118, day07a(readInputLines("day07", 2016)))
    }
}

class Day07bTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day07b(listOf("aba[bab]xyz")))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day07b(listOf("xyx[xyx]xyx")))
    }

    @Test fun testExampleInput3() {
        assertEquals(1, day07b(listOf("aaa[kek]eke")))
    }

    @Test fun testExampleInput4() {
        assertEquals(1, day07b(listOf("zazbz[bzb]cdb")))
    }

    @Test fun testActualInput() {
        assertEquals(260, day07b(readInputLines("day07", 2016)))
    }
}
