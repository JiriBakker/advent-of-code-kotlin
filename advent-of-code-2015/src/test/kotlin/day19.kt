import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day19a(listOf(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOH")))
    }

    @Test fun testExampleInput2() {
        assertEquals(7, day19a(listOf(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOHOHO")))
    }

    @Test fun testActualInput() {
        assertEquals(509, day19a(readInputLines("day19")))
    }
}

class Day19bTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day19b(listOf(
            "e => H",
            "e => O",
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOH")))
    }

    @Test fun testExampleInput2() {
        assertEquals(6, day19b(listOf(
            "e => H",
            "e => O",
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOHOHO")))
    }

    @Test fun testActualInput() {
        assertEquals(195, day19b(readInputLines("day19")))
    }
}