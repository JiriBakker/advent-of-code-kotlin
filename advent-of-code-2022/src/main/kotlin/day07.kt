import Day07.MAX_FILE_SIZE_AVAILABLE
import Day07.parseDirectoryStructure

fun day07a(input: List<String>): Long {
    val directories = input.parseDirectoryStructure()

    return directories.values
        .filter { it.size <= 100000 }
        .sumOf { it.size }
}

fun day07b(input: List<String>): Long {
    val directories = input.parseDirectoryStructure()

    val sizeToFree = directories["/"]!!.size - MAX_FILE_SIZE_AVAILABLE

    return directories.values
        .sortedBy { it.size }
        .first { it.size >= sizeToFree }
        .size
}

object Day07 {

    const val MAX_FILE_SIZE_AVAILABLE = 40000000

    abstract class FilesystemNode(val name: String, val parent: Directory?) {
        abstract val size: Long
    }

    class Directory(val path: String, name: String, parent: Directory?): FilesystemNode(name, parent) {
        val children = mutableMapOf<String, FilesystemNode>()

        override val size: Long get() = children.values.sumOf { it.size }
    }

    class File(override val size: Long, name: String, parent: Directory): FilesystemNode(name, parent)

    fun List<String>.parseDirectoryStructure(): Map<String, Directory> {
        var curDirectory = Directory("/", name = "", parent = null)
        val directories = mutableMapOf(
            "/" to curDirectory
        )

        fun findOrCreateSubdirectory(name: String): Directory {
            val path = curDirectory.path + name + "/"
            return directories.getOrPut(path) {
                Directory(path, name, curDirectory)
            }
        }

        var curLineIndex = 1
        while (curLineIndex < this.size) {
            when (this[curLineIndex].drop(2)) {
                "ls" -> {
                    val lines = this.drop(curLineIndex + 1).takeWhile { !it.startsWith("\$")}

                    lines.forEach { line ->
                        if (line.startsWith("dir ")) {
                            val subdirectory = findOrCreateSubdirectory(line.drop(4))
                            curDirectory.children[subdirectory.name] = subdirectory
                        } else {
                            val (size, name) = line.split(" ")
                            curDirectory.children[name] = File(size.toLong(), name, curDirectory)
                        }
                    }

                    curLineIndex += lines.size
                }

                "cd .." ->
                    curDirectory = curDirectory.parent!!

                else ->
                    curDirectory = findOrCreateSubdirectory(this[curLineIndex].drop(5))
            }
            curLineIndex++
        }

        return directories
    }

}

