import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day17aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(102, day17a(listOf(
            "2413432311323",
            "3215453535623",
            "3255245654254",
            "3446585845452",
            "4546657867536",
            "1438598798454",
            "4457876987766",
            "3637877979653",
            "4654967986887",
            "4564679986453",
            "1224686865563",
            "2546548887735",
            "4322674655533")))
    }

    @Test
    fun testActualInput() {
        assertEquals(638, day17a(readInputLines("day17")))
    }
}

class Day17bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(94, day17b(listOf(
            "2413432311323",
            "3215453535623",
            "3255245654254",
            "3446585845452",
            "4546657867536",
            "1438598798454",
            "4457876987766",
            "3637877979653",
            "4654967986887",
            "4564679986453",
            "1224686865563",
            "2546548887735",
            "4322674655533")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(71, day17b(listOf(
            "111111111111",
            "999999999991",
            "999999999991",
            "999999999991",
            "999999999991")))
    }

    @Test
    fun testActualInput() {
        assertEquals(748, day17b(readInputLines("day17")))
    }
}
