package v2016.days.day06

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals("easter", day06a(listOf(
            "eedadn",
            "drvtee",
            "eandsr",
            "raavrd",
            "atevrs",
            "tsrnev",
            "sdttsa",
            "rasrtv",
            "nssdts",
            "ntnada",
            "svetve",
            "tesnvt",
            "vntsnd",
            "vrdear",
            "dvrsen",
            "enarar"
        )))
    }

    @Test fun testActualInput() {
        assertEquals("liwvqppc", day06a(readInputLines("day06", 2016)))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals("advent", day06b(listOf(
            "eedadn",
            "drvtee",
            "eandsr",
            "raavrd",
            "atevrs",
            "tsrnev",
            "sdttsa",
            "rasrtv",
            "nssdts",
            "ntnada",
            "svetve",
            "tesnvt",
            "vntsnd",
            "vrdear",
            "dvrsen",
            "enarar"
        )))
    }

    @Test fun testActualInput() {
        assertEquals("caqfbzlh", day06b(readInputLines("day06", 2016)))
    }
}
