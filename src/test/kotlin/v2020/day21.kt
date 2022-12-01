package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day21aTests {
    @Test fun testExampleInput1() {
        assertEquals(5, day21a(listOf(
            "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)",
            "trh fvjkl sbzzf mxmxvkd (contains dairy)",
            "sqjhc fvjkl (contains soy)",
            "sqjhc mxmxvkd sbzzf (contains fish)")))
    }

    @Test fun testActualInput() {
        assertEquals(2584, day21a(readInputLines("day21", 2020)))
    }
}

class Day21bTests {
    @Test fun testExampleInput1() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", day21b(listOf(
            "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)",
            "trh fvjkl sbzzf mxmxvkd (contains dairy)",
            "sqjhc fvjkl (contains soy)",
            "sqjhc mxmxvkd sbzzf (contains fish)")))
    }

    @Test fun testActualInput() {
        assertEquals("fqhpsl,zxncg,clzpsl,zbbnj,jkgbvlxh,dzqc,ppj,glzb", day21b(readInputLines("day21", 2020)))
    }
}
