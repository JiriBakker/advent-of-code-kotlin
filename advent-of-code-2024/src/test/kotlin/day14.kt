import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(12, day14a(listOf(
            "p=0,4 v=3,-3",
            "p=6,3 v=-1,-3",
            "p=10,3 v=-1,2",
            "p=2,0 v=2,-1",
            "p=0,0 v=1,3",
            "p=3,0 v=-2,-2",
            "p=7,6 v=-1,-3",
            "p=3,0 v=-1,-2",
            "p=9,3 v=2,3",
            "p=7,3 v=-1,2",
            "p=2,4 v=2,-3",
            "p=9,5 v=-3,-3"), maxX=11, maxY=7))
    }


    @Test
    fun testActualInput() {
        assertEquals(226236192, day14a(readInputLines("day14")))
    }
}

class Day14bTests {
    @Test
    fun testActualInput() {
        assertEquals(8168, day14b(readInputLines("day14")))
    }
}
