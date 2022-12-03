import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day05aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day05a(listOf("ugknbfddgicrmopn")))
    }

    @Test fun testExampleInput2() {
        assertEquals(1, day05a(listOf("aaa")))
    }

    @Test fun testExampleInput3() {
        assertEquals(0, day05a(listOf("jchzalrnumimnmhp")))
    }

    @Test fun testExampleInput4() {
        assertEquals(0, day05a(listOf("haegwjzuvuyypxyu")))
    }

    @Test fun testExampleInput5() {
        assertEquals(0, day05a(listOf("dvszwmarrgswjxmb")))
    }

    @Test fun testActualInput() {
        assertEquals(258, day05a(readInputLines("day05")))
    }
}

class Day05bTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day05b(listOf("qjhvhtzxzqqjkmpb")))
    }

    @Test fun testExampleInput2() {
        assertEquals(1, day05b(listOf("xxyxx")))
    }

    @Test fun testExampleInput3() {
        assertEquals(0, day05b(listOf("uurcxstgmygtbstg")))
    }

    @Test fun testExampleInput4() {
        assertEquals(0, day05b(listOf("ieodomkazucvgmuy")))
    }

    @Test fun testActualInput() {
        assertEquals(53, day05b(readInputLines("day05")))
    }
}
