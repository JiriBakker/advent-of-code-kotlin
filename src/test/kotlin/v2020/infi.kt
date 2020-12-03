package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.extra.infiA
import v2020.extra.infiB

class InfiATests {
    @Test fun test() {
        assertEquals(1581, infiA())
    }
}

class InfiBTests {
    @Test fun test() {
        assertEquals(537816, infiB())
    }
}
