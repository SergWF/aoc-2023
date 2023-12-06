fun day6_1(lines: List<String>): Long = lines.map { it.extractNumbers() }
    .let { it[0].zip(it[1]) }
    .map { (time, maxDest) -> getWins(time, maxDest) }
    .fold(1) { acc, e -> acc * e }


fun day6_2(lines: List<String>): Int =
    lines.map { it.split(':')[1].replace(" ", "").toLong() }.let { getWins(it[0], it[1]) }


private fun getWins(time: Long, maxDistance: Long): Int =
    (1..<time).map { t -> time * t - t * t }.count { it > maxDistance }
