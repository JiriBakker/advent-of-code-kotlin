package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day25a(listOf(
            "Begin in state A.",
            "Perform a diagnostic checksum after 6 steps.",
            "",
            "In state A:",
            "  If the current value is 0:",
            "    - Write the value 1.",
            "    - Move one slot to the right.",
            "    - Continue with state B.",
            "  If the current value is 1:",
            "    - Write the value 0.",
            "    - Move one slot to the left.",
            "    - Continue with state B.",
            "",
            "In state B:",
            "  If the current value is 0:",
            "    - Write the value 1.",
            "    - Move one slot to the left.",
            "    - Continue with state A.",
            "  If the current value is 1:",
            "    - Write the value 1.",
            "    - Move one slot to the right.",
            "    - Continue with state A."
        )))
    }

    @Test fun testActualInput() {
        assertEquals(3745, day25a(readInputLines("day25", 2017)))
    }
}
