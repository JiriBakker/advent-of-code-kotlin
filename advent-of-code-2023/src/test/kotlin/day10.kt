import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day10aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4, day10a(listOf(
            "-L|F7",
            "7S-7|",
            "L|7||",
            "-L-J|",
            "L|-JF")))
    }


    @Test
    fun testExampleInput2() {
        assertEquals(8, day10a(listOf(
            "7-F7-",
            ".FJ|7",
            "SJLL7",
            "|F--J",
            "LJ.LJ")))
    }

    @Test
    fun testActualInput() {
        assertEquals(7086, day10a(readInputLines("day10")))
    }
}

class Day10bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(4, day10b(listOf(
            "...........",
            ".S-------7.",
            ".|F-----7|.",
            ".||.....||.",
            ".||.....||.",
            ".|L-7.F-J|.",
            ".|..|.|..|.",
            ".L--J.L--J.",
            "...........")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(4, day10b(listOf(
            "..........",
            ".S------7.",
            ".|F----7|.",
            ".||....||.",
            ".||....||.",
            ".|L-7F-J|.",
            ".|..||..|.",
            ".L--JL--J.",
            "..........")))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(8, day10b(listOf(
            ".F----7F7F7F7F-7....",
            ".|F--7||||||||FJ....",
            ".||.FJ||||||||L7....",
            "FJL7L7LJLJ||LJ.L-7..",
            "L--J.L7...LJS7F-7L7.",
            "....F-J..F7FJ|L7L7L7",
            "....L7.F7||L7|.L7L7|",
            ".....|FJLJ|FJ|F7|.LJ",
            "....FJL-7.||.||||...",
            "....L---J.LJ.LJLJ...")))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(10, day10b(listOf(
            "FF7FSF7F7F7F7F7F---7",
            "L|LJ||||||||||||F--J",
            "FL-7LJLJ||||||LJL-77",
            "F--JF--7||LJLJ7F7FJ-",
            "L---JF-JLJ.||-FJLJJ7",
            "|F|F-JF---7F7-L7L|7|",
            "|FFJF7L7F-JF7|JL---7",
            "7-L-JL7||F7|L7F-7F7|",
            "L.L7LFJ|||||FJL7||LJ",
            "L7JLJL-JLJLJL--JLJ.L")))
    }

    @Test
    fun testActualInput() {
        assertEquals(317, day10b(readInputLines("day10")))
    }

}
