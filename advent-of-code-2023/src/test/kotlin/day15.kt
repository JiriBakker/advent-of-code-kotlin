import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(52, day15a(listOf(
            "HASH")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(1320, day15a(listOf(
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")))
    }

    @Test
    fun testActualInput() {
        assertEquals(517015, day15a(readInputLines("day15")))
    }
}

class Day15bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(145, day15b(listOf(
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")))
    }

    @Test
    fun testActualInput() {
        assertEquals(286104, day15b(readInputLines("day15")))
    }
}
