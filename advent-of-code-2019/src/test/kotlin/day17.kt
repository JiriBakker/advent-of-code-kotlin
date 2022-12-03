import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day17aTests {
    @Test fun testActualInput() {
        assertEquals(3608, day17a(readInputLine("day17")))
    }
}

class Day17bTests {
    @Test fun testActualInput_hardcoded() {
        assertEquals(897426, day17b_hardcoded(readInputLine("day17")))
    }

    @Test fun testActualInput_compute() {
        assertEquals(897426, day17b_compute(readInputLine("day17")))
    }
}