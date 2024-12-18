import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day17aTests {
    @Test
    fun testExampleInput1() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", day17a(listOf(
            "Register A: 729",
            "Register B: 0",
            "Register C: 0",
            "",
            "Program: 0,1,5,4,3,0")))
    }

    @Test
    fun testActualInput() {
        assertEquals("2,1,3,0,5,2,3,7,1", day17a(readInputLines("day17")))
    }
}

class Day17bTests {

  @Test
    fun testExampleInput1() {
        assertEquals(117440, day17b(listOf(
            "Register A: 2024",
            "Register B: 0",
            "Register C: 0",
            "",
            "Program: 0,3,5,4,3,0")))
    }

    @Test
    fun testActualInput() {
        assertEquals(107416732707226, day17b(readInputLines("day17")))
    }
}
