package v2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(739785, day21a(listOf(
            "Player 1 starting position: 4",
            "Player 2 starting position: 8"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(605070, day21a(readInputLines("day21", 2021)))
    }
}

class Day21bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(444356092776315, day21b(listOf(
            "Player 1 starting position: 4",
            "Player 2 starting position: 8"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(218433063958910, day21b(readInputLines("day21", 2021)))
    }
}
