import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day22aTests {
    @Test fun testActualInput() {
        assertEquals(1003, day22a(readInputLines("day22")))
    }
}

class Day22bTests {
    @Test fun testActualInput() {
        assertEquals(192, day22b(readInputLines("day22")))
    }
}
