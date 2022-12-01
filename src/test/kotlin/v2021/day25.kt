package v2021

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day25aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(58, day25a(listOf(
            "v...>>.vv>",
            ".vv>>.vv..",
            ">>.>v>...v",
            ">>v>>.>.v.",
            "v>v.vv.v..",
            ">.>>..v...",
            ".vv..>.>v.",
            "v.v..>>v.v",
            "....v..v.>"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(582, day25a(readInputLines("day25", 2021)))
    }
}