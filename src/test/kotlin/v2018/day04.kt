package v2018.days.day04

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day04aTests {
    @Test fun testExampleInput1() {
        assertEquals(240, day04a(
            listOf(
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:05] falls asleep",
                "[1518-11-01 00:25] wakes up",
                "[1518-11-01 00:30] falls asleep",
                "[1518-11-01 00:55] wakes up",
                "[1518-11-01 23:58] Guard #99 begins shift",
                "[1518-11-02 00:40] falls asleep",
                "[1518-11-02 00:50] wakes up",
                "[1518-11-03 00:05] Guard #10 begins shift",
                "[1518-11-03 00:24] falls asleep",
                "[1518-11-03 00:29] wakes up",
                "[1518-11-04 00:02] Guard #99 begins shift",
                "[1518-11-04 00:36] falls asleep",
                "[1518-11-04 00:46] wakes up",
                "[1518-11-05 00:03] Guard #99 begins shift",
                "[1518-11-05 00:45] falls asleep",
                "[1518-11-05 00:55] wakes up"
            )
        )
        )
    }

    @Test fun testActualInput() {
        assertEquals(103720, day04a(readInputLines("day04", 2018)))
    }
}

class Day04bTests {
    @Test fun testExampleInput1() {
        assertEquals(4455, day04b(
            listOf(
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:05] falls asleep",
                "[1518-11-01 00:25] wakes up",
                "[1518-11-01 00:30] falls asleep",
                "[1518-11-01 00:55] wakes up",
                "[1518-11-01 23:58] Guard #99 begins shift",
                "[1518-11-02 00:40] falls asleep",
                "[1518-11-02 00:50] wakes up",
                "[1518-11-03 00:05] Guard #10 begins shift",
                "[1518-11-03 00:24] falls asleep",
                "[1518-11-03 00:29] wakes up",
                "[1518-11-04 00:02] Guard #99 begins shift",
                "[1518-11-04 00:36] falls asleep",
                "[1518-11-04 00:46] wakes up",
                "[1518-11-05 00:03] Guard #99 begins shift",
                "[1518-11-05 00:45] falls asleep",
                "[1518-11-05 00:55] wakes up"
            )
        )
        )
    }

    @Test fun testWakesUpOverlap1() {
        assertEquals(3000, day04b(
            listOf(
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:01] falls asleep",
                "[1518-11-01 00:02] wakes up",
                "[1518-11-02 00:00] Guard #100 begins shift",
                "[1518-11-02 00:02] falls asleep",
                "[1518-11-02 00:03] wakes up",
                "[1518-11-03 00:00] Guard #1000 begins shift",
                "[1518-11-03 00:03] falls asleep",
                "[1518-11-03 00:04] wakes up",
                "[1518-11-04 00:00] Guard #1000 begins shift",
                "[1518-11-04 00:03] falls asleep",
                "[1518-11-04 00:04] wakes up"
            )
        )
        )
    }

    @Test fun testWakesUpOverlap2() {
        assertEquals(10, day04b(
            listOf(
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:01] falls asleep",
                "[1518-11-01 00:02] wakes up",
                "[1518-11-02 00:00] Guard #10 begins shift",
                "[1518-11-02 00:01] falls asleep",
                "[1518-11-02 00:02] wakes up",
                "[1518-11-03 00:00] Guard #100 begins shift",
                "[1518-11-03 00:02] falls asleep",
                "[1518-11-03 00:03] wakes up",
                "[1518-11-04 00:00] Guard #1000 begins shift",
                "[1518-11-04 00:03] falls asleep",
                "[1518-11-04 00:04] wakes up"
            )
        )
        )
    }

    @Test fun testActualInput() {
        assertEquals(110913, day04b(readInputLines("day04", 2018)))
    }
}