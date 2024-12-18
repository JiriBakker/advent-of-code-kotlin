package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.days.day07.day07a
import v2020.days.day07.day07b
import util.readInputLines

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day07a(listOf(
            "light red bags contain 1 bright white bag, 2 muted yellow bags.",
            "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
            "bright white bags contain 1 shiny gold bag.",
            "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
            "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
            "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
            "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
            "faded blue bags contain no other bags.",
            "dotted black bags contain no other bags.")))
    }

    @Test fun testActualInput() {
        assertEquals(124, day07a(readInputLines("day07", 2020)))
    }
}

class Day07bTests {
    @Test fun testExampleInput1() {
        assertEquals(32, day07b(listOf(
            "light red bags contain 1 bright white bag, 2 muted yellow bags.",
            "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
            "bright white bags contain 1 shiny gold bag.",
            "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
            "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
            "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
            "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
            "faded blue bags contain no other bags.",
            "dotted black bags contain no other bags.")))
    }

    @Test fun testExampleInput2() {
        assertEquals(126, day07b(listOf(
            "shiny gold bags contain 2 dark red bags.",
                "dark red bags contain 2 dark orange bags.",
                "dark orange bags contain 2 dark yellow bags.",
                "dark yellow bags contain 2 dark green bags.",
                "dark green bags contain 2 dark blue bags.",
                "dark blue bags contain 2 dark violet bags.",
                "dark violet bags contain no other bags.")))
    }

    @Test fun testActualInput() {
        assertEquals(34862, day07b(readInputLines("day07", 2020)))
    }
}
