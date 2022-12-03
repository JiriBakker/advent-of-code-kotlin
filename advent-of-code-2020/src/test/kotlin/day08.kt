import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day08aTests {
    @Test fun testExampleInput1() {
        assertEquals(5, day08a(listOf(
            "nop +0",
            "acc +1",
            "jmp +4",
            "acc +3",
            "jmp -3",
            "acc -99",
            "acc +1",
            "jmp -4",
            "acc +6")))
    }

    @Test fun testActualInput() {
        assertEquals(2034, day08a(readInputLines("day08")))
    }
}

class Day08bTests {
    @Test fun testExampleInput1() {
        assertEquals(8, day08b(listOf(
            "nop +0",
            "acc +1",
            "jmp +4",
            "acc +3",
            "jmp -3",
            "acc -99",
            "acc +1",
            "jmp -4",
            "acc +6")))
    }

    @Test fun testActualInput() {
        assertEquals(672, day08b(readInputLines("day08")))
    }
}
