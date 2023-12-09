fun day9_1(lines: List<String>): Long =
    lines.map { it.toListOfLongs() }.onEach { print("$it ") }.sumOf { it.extrapolateNext() }


fun day9_2(lines: List<String>): Long =
    lines.map { it.toListOfLongs() }
        .onEach { print("$it ") }.also { println("\n\n") }
        .sumOf { it.extrapolatePrev() }


fun List<Long>.extrapolateNext(): Long {
    val deltas = this.zipWithNext { a, b -> b - a }
    return this.last() + if (deltas.all { it == deltas[0] }) {
        deltas[0]
    } else {
        deltas.extrapolateNext()
    }
}

fun List<Long>.extrapolatePrev(): Long {
    val deltas = this.zipWithNext { a, b -> b - a }
    val root = if (deltas.all { it == deltas[0] }) {
        deltas[0]
    } else {
        deltas.extrapolatePrev()
    }
    return this.first() - root
}

private fun String.toListOfLongs() = this.split(" ").filter{it.isNotBlank()}.map { it.toLong() }