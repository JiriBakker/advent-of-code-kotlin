import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day09a("ADVENT"))
    }

    @Test fun testExampleInput2() {
        assertEquals(7, day09a("A(1x5)BC"))
    }

    @Test fun testExampleInput3() {
        assertEquals(9, day09a("(3x3)XYZ"))
    }

    @Test fun testExampleInput4() {
        assertEquals(11, day09a("A(2x2)BCD(2x2)EFG"))
    }

    @Test fun testExampleInput5() {
        assertEquals(6, day09a("(6x1)(1x3)A"))
    }

    @Test fun testExampleInput6() {
        assertEquals(18, day09a("X(8x2)(3x3)ABCY"))
    }

    @Test fun testActualInput() {
        assertEquals(120765, day09a(readInputLine("day09")))
    }
}

class Day09bTests {
    @Test fun testExampleInput1() {
        assertEquals(9, day09b("(3x3)XYZ"))
    }

    @Test fun testExampleInput2() {
        assertEquals(20, day09b("X(8x2)(3x3)ABCY"))
    }

    @Test fun testExampleInput3() {
        assertEquals(241920, day09b("(27x12)(20x12)(13x14)(7x10)(1x12)A"))
    }

    @Test fun testExampleInput4() {
        assertEquals(445, day09b("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"))
    }

    @Test fun testActualInput() {
        assertEquals(11658395076, day09b(readInputLine("day09")))
    }
}

