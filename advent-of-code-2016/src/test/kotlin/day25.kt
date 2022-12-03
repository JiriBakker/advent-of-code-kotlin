import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test fun testActualInput() {
        assertEquals(180, day25a(readInputLines("day25", 2016)))
    }
}
