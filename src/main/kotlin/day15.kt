fun day15_1(lines: List<String>): Int = lines.map { it.split(",") }.flatten().sumOf { it.aocHash() }

fun day15_2(lines: List<String>): Int = lines.count()


fun String.aocHash(): Int = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }