package days.day21

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

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
