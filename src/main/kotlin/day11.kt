import kotlin.math.abs


fun day11_1(lines: List<String>): Long  = day11(lines, 2)
fun day11_2(lines: List<String>): Long  = day11(lines, 1_000_000)


private fun day11(lines: List<String>, expansion: Long): Long {
    printData(lines)
    val galaxies = lines.extractGalaxies().expandGalaxiesByX(lines, expansion).expandGalaxiesByY(lines, expansion)
    return galaxies.sumOf { galaxy -> galaxies.distances(galaxy) } / 2
}



private const val emptySpace = '.'
private const val galaxySign = '#'

private fun List<Galaxy>.expandGalaxiesByY(universe: List<String>, range: Long): List<Galaxy> {
    universe.mapIndexed { idx, l -> idx to l.containsOnly(emptySpace) }
        .filter { it.second }
        .map { it.first }.sorted().reversed()
        .forEach { pos -> this.filter { gal -> gal.y > pos }.forEach { gal -> gal.y += range - 1 } }
    return this
}

private fun List<Galaxy>.expandGalaxiesByX(universe: List<String>, range: Long): List<Galaxy> {
    (0..universe.first().lastIndex)
        .map { idx -> idx to universe.getColumn(idx) }
        .filter { it.second.containsOnly(emptySpace) }
        .map { it.first }
        .reversed()
        .forEach { pos -> this.filter { gal -> gal.x > pos }.forEach { gal -> gal.x += range - 1 } }
    return this
}

private fun List<String>.getColumn(index: Int): String = this.map { it[index] }.joinToString("")


private fun List<String>.extractGalaxies(): List<Galaxy> =
    this.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, c -> if (c == galaxySign) Galaxy(x.toLong(), y.toLong()) else null }
    }.filter { it.isNotEmpty() }.flatten()


private fun List<Galaxy>.distances(galaxy: Galaxy): Long =
    this.filter { it != galaxy }.sumOf { gal -> galaxy.distanceTo(gal) }


data class Galaxy(var x: Long, var y: Long) {
    fun distanceTo(galaxy: Galaxy): Long = abs(this.x - galaxy.x) + abs(this.y - galaxy.y)
}


