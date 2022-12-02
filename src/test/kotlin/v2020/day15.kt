package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test fun testExampleInput1() {
        assertEquals(436, day15a(listOf(
            "0,3,6")))
    }

    @Test fun testExampleInput2() {
        assertEquals(1, day15a(listOf(
            "1,3,2")))
    }

    @Test fun testExampleInput3() {
        assertEquals(10, day15a(listOf(
            "2,1,3")))
    }

    @Test fun testExampleInput4() {
        assertEquals(27, day15a(listOf(
            "1,2,3")))
    }

    @Test fun testExampleInput5() {
        assertEquals(78, day15a(listOf(
            "2,3,1")))
    }

    @Test fun testExampleInput6() {
        assertEquals(438, day15a(listOf(
            "3,2,1")))
    }

    @Test fun testExampleInput7() {
        assertEquals(1836, day15a(listOf(
            "3,1,2")))
    }

    @Test fun testActualInput() {
        assertEquals(403, day15a(readInputLines("day15", 2020)))
    }
}

class Day15bTests {
    @Test fun testExampleInput1() {
        assertEquals(175594, day15b(listOf(
            "0,3,6")))
    }

    @Test fun testExampleInput2() {
        assertEquals(2578, day15b(listOf(
            "1,3,2")))
    }

    @Test fun testExampleInput3() {
        assertEquals(3544142, day15b(listOf(
            "2,1,3")))
    }

    @Test fun testExampleInput4() {
        assertEquals(261214, day15b(listOf(
            "1,2,3")))
    }

    @Test fun testExampleInput5() {
        assertEquals(6895259, day15b(listOf(
            "2,3,1")))
    }

    @Test fun testExampleInput6() {
        assertEquals(18, day15b(listOf(
            "3,2,1")))
    }

    @Test fun testExampleInput7() {
        assertEquals(362, day15b(listOf(
            "3,1,2")))
    }

    @Test fun testActualInput() {
        assertEquals(6823, day15b(readInputLines("day15", 2020)))
    }
}
