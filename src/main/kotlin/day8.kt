import java.time.Instant

fun day8_1(lines: List<String>): Int {

    val instructions = Instructions(lines[0].trim())
    val nodes = parseData(lines.subList(1, lines.size))
    val trip = Trip(nodes, instructions)

    var i = 1L
    println("instructions: ${instructions.seed}")
    while (trip.hasNext() && i++ < 1_000_000) {
        trip.nextNode()

    }
    return trip.tripLength
}

fun day8_2(lines: List<String>): Int {
    val nodes = parseData(lines.subList(1, lines.size))
    val trips = nodes.filter { it.key.endsWith("A") }.map { Trip2(nodes, Instructions(lines[0].trim()), it.key) }

    println("instructions: ${trips[0].instructions.seed}")
    println("trips: ${trips.size}: ${trips.map { it.start }}")
    var i = 0L
    val startTime = Instant.now().epochSecond
    var time = startTime
    while(!isFinish(trips) && i++ < Long.MAX_VALUE) {
        if(time != Instant.now().epochSecond - startTime) println("$time sec, $i")
        time = Instant.now().epochSecond - startTime
        trips.forEachIndexed {idx, t ->
//            print("trip #$idx")
            t.nextNode()
        }
    }
    return trips[0].tripLength
}

private fun isFinish(trips: List<Trip2>): Boolean = trips.all { it.currentNode.endsWith("Z") }

private fun parseData(data: List<String>) =
    data.filter { it.isNotBlank() }
        .map { it.split('=', '(', ',', ')').filter { it.isNotBlank() }.map { it.trim() } }
        .associate { it[0] to mapOf('L' to it[1], 'R' to it[2]) }

private class Trip(val nodes: Map<String, Map<Char, String>>, val instructions: Instructions) {
    val start = "AAA"
    val end = "ZZZ"
    var currentNode: String
    var tripLength: Int = 0

    init {
        currentNode = start
    }

    fun hasNext(): Boolean {
        println("hasnext: $currentNode ${nodes[currentNode]}")
        return currentNode != end
    }

    fun nextNode(): String {
        val prevNode = currentNode
        val instruction = instructions.next()
        currentNode = nodes[prevNode]!![instruction]!!
        tripLength++
        if (prevNode == currentNode) throw IllegalStateException("Circular! $currentNode  ${nodes[currentNode]}")
        println("step: $prevNode ${nodes[prevNode]} [$instruction] -> $currentNode ${nodes[currentNode]}")
        return currentNode
    }
}

private class Trip2(val nodes: Map<String, Map<Char, String>>, val instructions: Instructions, val start: String) {
    var currentNode: String = start
    var tripLength: Int = 0

    fun nextNode(): String {
        val prevNode = currentNode
        val instruction = instructions.next()
//        println(" go from $prevNode to  $instruction => ${nodes[prevNode]}")
        currentNode = nodes[prevNode]!![instruction]!!
        tripLength++
        if (prevNode == currentNode) throw IllegalStateException("Circular! $currentNode  ${nodes[currentNode]}")
//        println("step: $prevNode ${nodes[prevNode]} [$instruction] -> $currentNode ${nodes[currentNode]}")
        return currentNode
    }

}


private class Instructions(val seed: String) {
    var idx = 0
    fun next(): Char {
        if (idx > seed.lastIndex) idx = 0
        return seed[idx++]
    }
}

