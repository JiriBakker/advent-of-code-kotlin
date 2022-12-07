import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day07aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(95437, day07a(listOf(
            "\$ cd /",
            "\$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "\$ cd a",
            "\$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "\$ cd e",
            "\$ ls",
            "584 i",
            "\$ cd ..",
            "\$ cd ..",
            "\$ cd d",
            "\$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1367870, day07a(readInputLines("day07")))
    }
}

class Day07bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(24933642, day07b(listOf(
            "\$ cd /",
            "\$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "\$ cd a",
            "\$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "\$ cd e",
            "\$ ls",
            "584 i",
            "\$ cd ..",
            "\$ cd ..",
            "\$ cd d",
            "\$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k")))
    }

    @Test
    fun testActualInput() {
        assertEquals(549173, day07b(readInputLines("day07")))
    }
}
