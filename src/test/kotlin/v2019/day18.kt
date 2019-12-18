package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day18.day18a
import v2019.days.day18.day18b
import v2019.util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(8, day18a(listOf(
            "#########",
            "#b.A.@.a#",
            "#########")))
    }

     @Test fun testExampleInput2() {
        assertEquals(86, day18a(listOf(
            "########################",
            "#f.D.E.e.C.b.A.@.a.B.c.#",
            "######################.#",
            "#d.....................#",
            "########################")))
    }

     @Test fun testExampleInput3() {
        assertEquals(132, day18a(listOf(
            "########################",
            "#...............b.C.D.f#",
            "#.######################",
            "#.....@.a.B.c.d.A.e.F.g#",
            "########################")))
    }

     @Test fun testExampleInput4() {
        assertEquals(136, day18a(listOf(
            "#################",
            "#i.G..c...e..H.p#",
            "########.########",
            "#j.A..b...f..D.o#",
            "########@########",
            "#k.E..a...g..B.n#",
            "########.########",
            "#l.F..d...h..C.m#",
            "#################")))
    }

    @Test fun testExampleInput5() {
        assertEquals(81, day18a(listOf(
            "########################",
            "#@..............ac.GI.b#",
            "###d#e#f################",
            "###A#B#C################",
            "###g#h#i################",
            "########################")))
    }

    @Test fun testActualInput() {
        assertEquals(3862, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(8, day18b(listOf(
            "#######",
            "#a.#Cd#",
            "##...##",
            "##.@.##",
            "##...##",
            "#cB#Ab#",
            "#######")))
    }

     @Test fun testExampleInput2() {
        assertEquals(24, day18b(listOf(
            "###############",
            "#d.ABC.#.....a#",
            "######...######",
            "######.@.######",
            "######...######",
            "#b.....#.....c#",
            "###############")))
    }

     @Test fun testExampleInput3() {
        assertEquals(32, day18b(listOf(
            "#############",
            "#DcBa.#.GhKl#",
            "#.###...#I###",
            "#e#d#.@.#j#k#",
            "###C#...###J#",
            "#fEbA.#.FgHi#",
            "#############")))
    }

     @Test fun testExampleInput4() {
        assertEquals(72, day18b(listOf(
            "#############",
            "#g#f.D#..h#l#",
            "#F###e#E###.#",
            "#dCba...BcIJ#",
            "#####.@.#####",
            "#nK.L...G...#",
            "#M###N#H###.#",
            "#o#m..#i#jk.#",
            "#############")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day18b(readInputLines("day18")))
    }
}