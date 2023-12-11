import kotlin.math.abs

fun day11_1(lines: List<String>): Int {
    val universe = lines.toMutableList().expandUniverseVertically().expandUniverseHorizontally()
    universe.forEach { println(it) }
    val galaxies = universe.extractGalaxies().also { println(it) }
    return galaxies.sumOf { galaxy -> galaxies.distances(galaxy) } /2
}

private const val emptySpace = '.'
private const val galaxy = '#'

private fun MutableList<String>.expandUniverseVertically(): MutableList<String> {
    this.mapIndexed { idx, l -> idx to l.containsOnly(emptySpace) }
        .filter { it.second }
        .map { it.first }
        .reversed().forEach { this.add(it, this[it]) }
    return this
}

private fun MutableList<String>.expandUniverseHorizontally(): MutableList<String> {
    val s = (0..this.first().lastIndex)
        .map { idx -> idx to getColumn(idx) }
        .filter { it.second.containsOnly(emptySpace) }
        .map { it.first }
    return this.map { it.insertInIndexes(s, emptySpace) }.toMutableList()
}

private fun List<String>.getColumn(index: Int): String =
    this.map { it[index] }.joinToString("")

private fun String.insertInIndexes(indexes: List<Int>, chr: Char) =
    (indexes + 0 + this.length).sorted()
        .zipWithNext()
        .joinToString(emptySpace.toString()) { border -> this.substring(border.first, border.second) }


fun List<String>.extractGalaxies(): List<Pair<Int, Int>> =
    this.mapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c == galaxy) Pair(x, y) else null } }
        .filter { it.isNotEmpty() }.flatten()


private fun distance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int = abs(a.first - b.first) + abs(a.second - b.second)
private fun List<Pair<Int, Int>>.distances(galaxy: Pair<Int, Int>): Int = this.filter { it != galaxy }.sumOf {gal-> distance(galaxy, gal) }


