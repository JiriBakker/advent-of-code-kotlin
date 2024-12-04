import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(161, day03a(listOf(
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")))
    }

    @Test
    fun testActualInput() {
        assertEquals(163931492, day03a(readInputLines("day03")))
    }
}

class Day03bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(48, day03b(listOf(
            "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")))
    }

    @Test
    fun testActualInput() {
        assertEquals(76911921, day03b(readInputLines("day03")))
    }
}
