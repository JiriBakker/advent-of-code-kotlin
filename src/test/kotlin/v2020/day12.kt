package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(25, day12a(listOf(
            "F10",
            "N3",
            "F7",
            "R90",
            "F11")))
    }

    @Test fun testCustomInput1() {
        assertEquals(0, day12a(listOf(
            "N10",
            "E10",
            "S10",
            "W10",
            "R90",
            "F10",
            "L270",
            "F10",
            "L180",
            "F10",
            "R270",
            "F10")))
    }

    @Test fun testCustomInput2() {
        assertEquals(30, day12a(listOf(
            "N10",   //  0, -10, E
            "E20",   // 20, -10, E
            "S10",   // 20,   0, E
            "W10",   // 10,   0, E
            "N20",   // 10, -20, E
            "F10",   // 20, -20, E
            "L270",  // 20, -20, S
            "F10",   // 20, -10, S
            "L180",  // 20, -10, N
            "F10",   // 20, -20, N
            "R270",  // 20, -20, W
            "F10"))) // 10, -20, W
    }

    @Test fun testCustomInput3() {
        assertEquals(0, day12a(listOf(
            "R0",    //   0,   0, E
            "F10",   //  10,   0, E
            "R90",   //  10,   0, S
            "F10",   //  10,  10, S
            "R180",  //  10,  10, N
            "F10",   //  10,   0, N
            "R270",  //  10,   0, W
            "F10",   //   0,   0, W
            "R360",  //   0,   0, W
            "F10",   // -10,   0, W
            "R450",  // -10,   0, N
            "F10",   // -10, -10, N
            "L0",    // -10, -10, N
            "F10",   // -10, -20, N
            "L90",   // -10, -20, W
            "F10",   // -20, -20, W
            "L180",  // -20, -20, E
            "F10",   // -10, -20, E
            "L270",  // -10, -20, S
            "F10",   // -10, -10, S
            "L360",  // -10, -10, S
            "F10",   // -10,   0, S
            "L450",  // -10,   0, E
            "F10"))) //   0,   0, E
    }

    @Test fun testActualInput() {
        assertEquals(1603, day12a(readInputLines("day12", 2020)))
    }
}

class Day12bTests {
    @Test fun testExampleInput1() {
        assertEquals(286, day12b(listOf(
            "F10",
            "N3",
            "F7",
            "R90",
            "F11")))
    }

    @Test fun testActualInput() {
        assertEquals(52866, day12b(readInputLines("day12", 2020)))
    }
}
