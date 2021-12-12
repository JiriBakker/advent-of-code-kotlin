package v2021

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLines

class Day12aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(10, day12a(listOf(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end"
        )))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(19, day12a(listOf(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc"
        )))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(226, day12a(listOf(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(4659, day12a(readInputLines("day12", 2021)))
    }
}

class Day12bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(36, day12b(listOf(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end"
        )))
    }


    @Test
    fun testExampleInput2() {
        assertEquals(103, day12b(listOf(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc"
        )))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(3509, day12b(listOf(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW"
        )))
    }


    @Test
    fun testActualInput() {
        assertEquals(148962, day12b(readInputLines("day12", 2021)))
    }
}
