import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day06aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(7, day06a(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(5, day06a(
            "bvwbjplbgvbhsrlpgdmjqwftvncz"))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(6, day06a(
            "nppdvjthqldpwncqszvftbrmjlhg"))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(10, day06a(
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
    }

    @Test
    fun testExampleInput5() {
        assertEquals(11, day06a(
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun testActualInput() {
        assertEquals(1647, day06a(readInputLine("day06")))
    }
}

class Day06bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(19, day06b(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(23, day06b(
            "bvwbjplbgvbhsrlpgdmjqwftvncz"))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(23, day06b(
            "nppdvjthqldpwncqszvftbrmjlhg"))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(29, day06b(
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
    }

    @Test
    fun testExampleInput5() {
        assertEquals(26, day06b(
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun testActualInput() {
        assertEquals(2447, day06b(readInputLine("day06")))
    }
}
