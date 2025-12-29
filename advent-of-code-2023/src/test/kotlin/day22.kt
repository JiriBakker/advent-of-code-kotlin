import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day22aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(5, day22a(listOf(
            "1,0,1~1,2,1",
            "0,0,2~2,0,2",
            "0,2,3~2,2,3",
            "0,0,4~0,2,4",
            "2,0,5~2,2,5",
            "0,1,6~2,1,6",
            "1,1,8~1,1,9")))
    }

    @Test
    fun testActualInput() {
        assertEquals(482, day22a(readInputLines("day22")))
    }
}

class Day22bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(7, day22b(listOf(
            "1,0,1~1,2,1",
            "0,0,2~2,0,2",
            "0,2,3~2,2,3",
            "0,0,4~0,2,4",
            "2,0,5~2,2,5",
            "0,1,6~2,1,6",
            "1,1,8~1,1,9")))
    }

    @Test
    fun testActualInput() {
        assertEquals(103010, day22b(readInputLines("day22")))
    }
}
