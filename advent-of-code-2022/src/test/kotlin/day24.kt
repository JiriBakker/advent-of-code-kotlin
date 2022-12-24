import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day24aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(18, day24a(listOf(
            "#.######",
            "#>>.<^<#",
            "#.<..<<#",
            "#>v.><>#",
            "#<^v^^>#",
            "######.#")))
    }

    @Test
    fun testActualInput() {
        assertEquals(290, day24a(readInputLines("day24")))
    }
}

class Day24bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(54, day24b(listOf(
            "#.######",
            "#>>.<^<#",
            "#.<..<<#",
            "#>v.><>#",
            "#<^v^^>#",
            "######.#")))
    }

    @Test
    fun testActualInput() {
        assertEquals(842, day24b(readInputLines("day24")))
    }
}
