fun day5_1(lines: List<String>): Long {
    val seeds = lines[0].split(':', ' ').minus("seeds").filter { it.isNotBlank() }.map { it.toLong() }
    val sections = sectionNames.map { getSection(it, lines) }
    return seeds.minOf { getSeedLocation(it, sections) }
}

fun day5_2(lines: List<String>): Long {
    val seeds = lines[0]
        .split(':', ' ')
        .minus("seeds")
        .filter { it.isNotBlank() }
        .map { it.toLong() }
        .zipWithNext()
        .filterIndexed { index, _ -> index % 2 == 0 }
        .map { LongRange(it.first, it.first + it.second - 1) }
    val sections = sectionNames.map { getSection(it, lines) }
    return seeds.minOf { range -> range.minOf { seed -> getSeedLocation(seed, sections) } }
}

val sectionNames = listOf(
    "seed-to-soil map",
    "soil-to-fertilizer map",
    "fertilizer-to-water map",
    "water-to-light map",
    "light-to-temperature map",
    "temperature-to-humidity map",
    "humidity-to-location map"
)


fun getSeedLocation(seed: Long, sections: List<Section>): Long =
    sections.fold(seed) { acc, section -> section.getDestination(acc) }


private val numberRegex = """\d+""".toRegex()

fun getSection(name: String, lines: List<String>): Section {
    val start = lines.indexOf("$name:") + 1
    val end = lines.getFirstIndexOf(start) { it.isBlank() }
    return Section(name, lines.subList(start, end).map { it.toMapping() })

}

data class Section(val name: String, val mappings: List<Mapping>) {
    fun getDestination(source: Long): Long = mappings.firstNotNullOfOrNull { it.findDestination(source) } ?: source
}

data class Mapping(val destinations: Long, val sources: Long, val length: Long) {
    fun belongsTo(source: Long): Boolean = (source >= sources) && (source <= sources + length - 1)
    fun findDestination(source: Long): Long? =
        if (belongsTo(source)) {
            destinations + (source - sources)
        } else null
}

private fun String.toMapping() = numberRegex.findAll(this)
    .map { it.value.toLong() }.toList().let { Mapping(it[0], it[1], it[2]) }

