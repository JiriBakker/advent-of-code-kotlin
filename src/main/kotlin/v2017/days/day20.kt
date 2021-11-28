package v2017.days.day20

import kotlin.math.abs

private fun parseParticles(input: List<String>): List<Particle> {
    fun parseVec3(string: String): Vec3 {
        val (x, y, z) = string.trim('<', '>').split(',').map(String::trim).map(String::toLong)
        return Vec3(x, y, z)
    }

    return input.mapIndexed { id, line ->
        val vectors = line.split(", ").map { parseVec3(it.split('=')[1]) }
        Particle(id, vectors[0], vectors[1], vectors[2])
    }
}

private data class Vec3(val x: Long, val y: Long, val z: Long) {
    fun manhattanDistanceTo(other: Vec3): Long = abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)
    fun plus(other: Vec3): Vec3 = Vec3(this.x + other.x, this.y + other.y, this.z + other.z)
}

private data class Particle(val id: Int, val pos: Vec3, val vel: Vec3, val acc: Vec3)

fun day20a(input: List<String>): Int {
    val particles = parseParticles(input)
    return particles.minByOrNull { abs(it.acc.x) + abs(it.acc.y) + abs(it.acc.z) }!!.id
}

private fun findIntersectionTime(particle1: Particle, particle2: Particle): Long? {
    var p1 = particle1.pos
    var v1 = particle1.vel
    val a1 = particle1.acc
    var p2 = particle2.pos
    var v2 = particle2.vel
    val a2 = particle2.acc
    var time = 0L

    do {
        if (p1.manhattanDistanceTo(p2) == 0L) {
            return time
        }
        v1 = v1.plus(a1)
        p1 = p1.plus(v1)
        v2 = v2.plus(a2)
        p2 = p2.plus(v2)

        time++
    } while (time < 50) // arbitrary limit, but works for given input (TODO compute somehow?)

    return null
}

fun day20b(input: List<String>): Int {
    val particles = parseParticles(input)

    val collisions = particles.indices.flatMap { i1 ->
        (i1 + 1 until particles.size).mapNotNull { i2 ->
            findIntersectionTime(particles[i1], particles[i2])?.let { it to (i1 to i2) }
        }
    }
        .groupBy({ it.first }) { it.second }
        .toSortedMap()

    val remainingParticles = particles.map { it.id }.toMutableSet()
    collisions.values.forEach { collidedParticles ->
        collidedParticles.filter { remainingParticles.contains(it.first) && remainingParticles.contains(it.second) }
            .forEach {
                remainingParticles.remove(it.first)
                remainingParticles.remove(it.second)
            }
    }

    return remainingParticles.count()
}

