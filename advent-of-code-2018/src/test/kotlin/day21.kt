import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test fun testActualInput() {
        assertEquals(10961197,
            day21a(readInputLines("day21"))
        )
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(8164934,
            day21b()
        )
    }
}
