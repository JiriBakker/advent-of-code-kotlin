import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day13aTests {
    @Test fun testExampleInput1() {
        assertEquals(295, day13a(listOf(
            "939",
            "7,13,x,x,59,x,31,19")))
    }

    @Test fun testActualInput() {
        assertEquals(138, day13a(readInputLines("day13")))
    }
}

class Day13bTests {
    @Test fun testExampleInput1() {
        assertEquals(1068781, day13b(listOf(
            "0",
            "7,13,x,x,59,x,31,19")))
    }

    @Test fun testExampleInput2() {
        assertEquals(3417, day13b(listOf(
            "0",
            "17,x,13,19")))
    }

    @Test fun testExampleInput3() {
        assertEquals(754018, day13b(listOf(
            "0",
            "67,7,59,61")))
    }

    @Test fun testExampleInput4() {
        assertEquals(779210, day13b(listOf(
            "0",
            "67,x,7,59,61")))
    }

    @Test fun testExampleInput5() {
        assertEquals(1261476, day13b(listOf(
            "0",
            "67,7,x,59,61")))
    }

    @Test fun testExampleInput6() {
        assertEquals(1202161486, day13b(listOf(
            "0",
            "1789,37,47,1889")))
    }

    @Test fun testCustomInput1() {
        assertEquals(2, day13b(listOf(
            "0",
            "2,3,4")))
    }

    @Test fun testActualInput() {
        assertEquals(226845233210288L, day13b(readInputLines("day13")))
    }
}
