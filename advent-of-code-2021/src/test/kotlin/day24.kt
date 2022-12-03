import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day24aTests {
    @Test
    fun testActualInput() {
        assertEquals(92928914999991L, day24a(readInputLines("day24")))
    }
}

class Day24bTests {

    @Test
    fun testActualInput() {
        assertEquals(91811211611981L, day24b(readInputLines("day24")))
    }
}
