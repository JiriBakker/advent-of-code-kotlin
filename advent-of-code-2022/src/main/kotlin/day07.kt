import Day07.parseDirectoryStructure

fun day07a(input: List<String>): Long {
    val directories = input.parseDirectoryStructure()

    return directories.values.filter { it.size <= 100000 }.sumOf { it.size }
}

fun day07b(input: List<String>): Long {
    val directories = input.parseDirectoryStructure()

    val sizeToFree = directories["/"]!!.size - 40000000
    val directorySizes = directories.values.map { it.size }.sorted()

    return directorySizes.dropWhile { it < sizeToFree }.first()
}

object Day07 {

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

        fun findOrCreateDirectory(name: String): Directory {
            val path = curDirectory.path + name + "/"
            return directories.getOrPut(path) {
                Directory(path, name, curDirectory)
            }
        }

        var curLineIndex = 1
        while (curLineIndex < this.size) {
            when (this[curLineIndex]) {
                "\$ ls" -> {
                    val lines = this.drop(curLineIndex + 1).takeWhile { !it.startsWith("\$")}

                    lines.forEach { line ->
                        if (line.startsWith("dir ")) {
                            val directory = findOrCreateDirectory(line.drop(4))
                            curDirectory.children[directory.name] = directory
                        } else {
                            val (size, name) = line.split(" ")
                            curDirectory.children[name] = File(size.toLong(), name, curDirectory)
                        }
                    }

                    curLineIndex += lines.size + 1
                }

                "\$ cd .." -> {
                    curDirectory = curDirectory.parent!!
                    curLineIndex++
                }

                else -> {
                    curDirectory = findOrCreateDirectory(this[curLineIndex].drop(5))
                    curLineIndex++
                }
            }
        }

        return directories
    }

}

