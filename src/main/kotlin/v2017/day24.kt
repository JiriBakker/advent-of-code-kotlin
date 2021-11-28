package v2017

private class Component(val port1: Int, val port2: Int) {
    private val ports = listOf(port1, port2)

    fun canConnectTo(port: Int): Boolean = ports.contains(port)
    fun openPortAfterConnect(port: Int): Int = ports.minus(port).first()
}

private fun parseComponents(input: List<String>): List<Component> {
    return input.map { line -> line.split('/').let { Component(it[0].toInt(), it[1].toInt()) } }
}

private fun combineComponents(port: Int, availableComponents: List<Component>): List<List<Component>> {
    val compatibleComponents = availableComponents.filter { component -> component.canConnectTo(port) }

    return compatibleComponents.flatMap { component ->
        combineComponents(component.openPortAfterConnect(port), availableComponents.minus(component))
            .map { listOf(component).plus(it) }
    }.plus(listOf(listOf()))
}

private fun constructBridges(components: List<Component>): List<Pair<List<Component>, Int>> {
    return combineComponents(0, components)
        .map { bridge -> bridge to bridge.sumOf { component -> component.port1 + component.port2 } }
}

fun day24a(input: List<String>): Int {
    val components = parseComponents(input)
    return constructBridges(components)
        .maxByOrNull { it.second }!!
        .second
}

fun day24b(input: List<String>): Int {
    val components = parseComponents(input)
    val bridgeStrengths = constructBridges(components)

    val maxBridgeLength = bridgeStrengths.map { it.first.size }.maxOrNull()!!

    return bridgeStrengths
        .filter { it.first.size == maxBridgeLength }
        .map { it.second }
        .maxOrNull()!!
}