package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day17.day17a
import v2019.days.day17.day17b_hardcoded
import v2019.days.day17.day17b_compute
import util.readInputLine

class Day17aTests {
    @Test fun testActualInput() {
        assertEquals(3608, day17a(readInputLine("day17", 2019)))
    }
}

class Day17bTests {
    @Test fun testActualInput_hardcoded() {
        assertEquals(897426, day17b_hardcoded(readInputLine("day17", 2019)))
    }

    @Test fun testActualInput_compute() {
        assertEquals(897426, day17b_compute(readInputLine("day17", 2019)))
    }
}