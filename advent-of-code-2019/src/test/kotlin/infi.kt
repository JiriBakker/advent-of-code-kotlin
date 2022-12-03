import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class InfiATests {
    @Test fun testExampleInput1() {
        assertEquals(4, infiA(
            listOf(
                "flats", "1,4", "3,8", "4,3", "5,7", "7,4", "10,3",
                "sprongen", "2,0", "0,4", "1,0", "0,0"
            )))
    }

    @Test fun testActualInput() {
        assertEquals(7, infiA(readInputLines("infi")))
    }
}

class InfiBTests {
    @Test fun testActualInput() {
        assertEquals(58, infiB(readInputLines("infi")))
    }
}
